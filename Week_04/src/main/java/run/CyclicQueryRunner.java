package run;

import task.Task;

public class CyclicQueryRunner implements AsyncTaskRunner{
    private Integer result;
    private Thread thread;
    private Task task;
    public void run(int param) {
        task = new Task(param);
        thread= new Thread(task);
        thread.run();


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
}
