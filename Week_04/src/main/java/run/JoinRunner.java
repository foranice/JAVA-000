package run;

import task.Task;

public class JoinRunner implements AsyncTaskRunner {
    private Integer result;
    private Thread thread;
    private Task task;
    public void run(int param) {
         task = new Task(param);
         thread= new Thread(task);
         thread.run();

    }
    public Integer get(){

        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return task.getResult();
    }
}
