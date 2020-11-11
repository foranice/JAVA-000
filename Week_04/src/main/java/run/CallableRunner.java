package run;

import task.Task;

import java.util.Queue;
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
    static {
        workQueye=new ArrayBlockingQueue<Runnable>(1000);
        executor = new ThreadPoolExecutor(20,30,3, TimeUnit.SECONDS,workQueye);
    }
}
