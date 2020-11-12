package run;

import java.util.Map;

public interface AsyncTaskRunner {
    void run(int param);
    Integer get();
    Map<String,Integer> getAll(Map<String,AsyncTaskRunner> tasks);
    static Map<String,Integer> getAll(Map<String,AsyncTaskRunner> tasks,Class cls){
        try{
            AsyncTaskRunner warp = (AsyncTaskRunner)cls.newInstance();
            return warp.getAll(tasks);
        }
        catch (Exception ex){
            ex.printStackTrace();;
        }
        return null;
    }
}
