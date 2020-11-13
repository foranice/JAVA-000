package run;

import helper.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class CallableRunner implements AsyncTaskRunner {
    private static ThreadPoolExecutor executor;
    private static BlockingQueue<Runnable> workQueye;
    private  Future<Integer> result;
    public void run(int param){
        Callable<Integer> task = new Task(param);
        result=executor.submit(task);

    }
    public Integer get(){
        try{
            return result.get();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;

    }
   public  Map<String,Integer> getAll(Map<String,AsyncTaskRunner> tasks){
       Map<String,Integer> result = new HashMap<>();
       for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
           result.put(task.getKey(),task.getValue().get());
       }
       return result;
   }

    static {
        workQueye=new ArrayBlockingQueue<Runnable>(1000);
        executor = new ThreadPoolExecutor(20,30,3, TimeUnit.SECONDS,workQueye);
    }
}
