package run;

import main.Main;
import helper.Task;

import java.util.HashMap;
import java.util.Map;

public class CountDownLatchRunner implements AsyncTaskRunner{
    private Thread thread;
    private Task task;
    @Override
    public void run(int param) {
        task = new CountDownTask(param);
        thread= new Thread(task);
        thread.start();
    }

    @Override
    public Integer get() {
        return task.getResult();
    }

    @Override
    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        try{
            Main.latch.await();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Map<String,Integer> result = new HashMap<>();
        for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
            result.put(task.getKey(),task.getValue().get());
        }
        return result;
    }
    class CountDownTask extends Task{
        CountDownTask(int param){
            super(param);
        }
        @Override
        public void run() {
            super.run();
            Main.latch.countDown();
        }

    }
}
