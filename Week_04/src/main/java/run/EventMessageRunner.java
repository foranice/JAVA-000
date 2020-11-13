package run;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import helper.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class EventMessageRunner implements AsyncTaskRunner{
    static AtomicInteger sequence = new AtomicInteger(1);
    int id = 0;
    final static Thread mainThread =Thread.currentThread();
    final static AsyncEventBus asyncEventBus=new AsyncEventBus(Executors.newFixedThreadPool(10));
    final static Map<Integer, Integer> resultMap =new ConcurrentHashMap<>();
    final static AtomicInteger count = new AtomicInteger(0);
    public EventMessageRunner(){
        this.id = sequence.getAndIncrement();
    }
    @Override
    public void run(int param) {
        EventMessageTask task = new EventMessageTask(param,id,asyncEventBus);
        Thread thread = new Thread(task);
        thread.start();
    }

    @Override
    public Integer get() {
        return null;
    }

    @Override
    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        while(true){
            if(count.get() == tasks.size()){
                Map<String,Integer> result = new HashMap<>();
                for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
                    EventMessageRunner runner = (EventMessageRunner)task.getValue();
                    result.put(task.getKey(),resultMap.get(runner.id));
                }
                return result;
            }
            else{
                LockSupport.park();
            }
        }

    }
    static {
        asyncEventBus.register(new Event());
    }

}
class Event{
    @Subscribe
    public void callBack(EventMessageTask task){
        EventMessageRunner.resultMap.put(task.runner_id,task.getResult());
        EventMessageRunner.count.getAndIncrement();
        LockSupport.unpark(EventMessageRunner.mainThread);
    }
}
class EventMessageTask extends Task {
    AsyncEventBus asyncEventBus;
    int runner_id;
    EventMessageTask(int param,int id,AsyncEventBus bus){
        super(param);
        asyncEventBus = bus;
        runner_id = id;
    }
    @Override
    public void run() {
        super.run();
        asyncEventBus.post(this);
    }


}