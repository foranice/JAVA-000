package run;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureRunner implements AsyncTaskRunner{
    private static ThreadPoolExecutor executor;
    private static BlockingQueue<Runnable> workQueye;
    public CompletableFuture<Integer> result=null;
    public void run(int param) {
        Supplier<Integer> task = new Task(param);
        result = CompletableFuture.supplyAsync(task,executor);
    }

    public Integer get() {
        return null;
    }

    @Override
    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        List<CompletableFuture<Integer>> list = new ArrayList<>();
        Map<String,Integer> keyIndexMap = new HashMap<>();
        CompletableFuture[] arr =new CompletableFuture[10];
        int index = 0;
        for(Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()){
            CompletableFutureRunner runner = (CompletableFutureRunner)task.getValue();
            list.add(runner.result);
            keyIndexMap.put(task.getKey(),index++);
        }
        CompletableFuture<Void> cfResult =CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
        Map<String,Integer> result = new HashMap<>();
        try{
            cfResult.get();
            for(Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()){
                result.put(task.getKey(),list.get(keyIndexMap.get(task.getKey())).get());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
        //result.get
    }

    static {
        workQueye=new ArrayBlockingQueue<Runnable>(1000);
        executor = new ThreadPoolExecutor(20,30,3, TimeUnit.SECONDS,workQueye);
    }
}
