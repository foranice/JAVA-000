package run;

import helper.Task;

import java.util.HashMap;
import java.util.Map;

public class CyclicQueryRunner implements AsyncTaskRunner{
    private Integer result;
    private Thread thread;
    private Task task;
    public void run(int param) {
        task = new Task(param);
        thread = new Thread(task);
        thread.start();


    }
    public Integer get(){
        while(true){
            if(!thread.isAlive()){
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
    public Map<String,Integer> getAll(Map<String,AsyncTaskRunner> tasks){
        Map<String,Integer> result = new HashMap<>();
        for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
            result.put(task.getKey(),task.getValue().get());
        }
        return result;
    }
}
