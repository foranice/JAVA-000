package run;

import main.Main;
import helper.Task;

import java.util.HashMap;
import java.util.Map;

public class CyclicBarrierRunner implements AsyncTaskRunner{
    public static volatile  boolean finish = false;
    private Thread thread;
    private CyclicBarrierTask task;
    @Override
    public void run(int param) {
        task = new CyclicBarrierTask(param);
        thread = new Thread(task);
        thread.start();
    }

    @Override
    public Integer get() {
        while(true){
            if(finish){
                break;
            }
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        return task.getResult();
    }

    @Override
    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        while(true){
            if(finish){
                break;
            }
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        Map<String,Integer> result = new HashMap<>();
        for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
            result.put(task.getKey(),task.getValue().get());
        }
        return result;
    }
    class CyclicBarrierTask extends Task{
        CyclicBarrierTask(int param){
            super(param);
        }
        @Override
        public void run() {
            super.run();
            try{
                Main.barrier.await();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
