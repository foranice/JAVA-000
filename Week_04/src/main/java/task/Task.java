package task;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Task implements Callable<Integer>,Runnable, Supplier<Integer> {
    private Integer result;
    private int param=36;
    public Task(){

    }
    public Task(int param){
        this.param=param;
    }
    public Integer getResult(){
        return result;
    }
    public Integer call() throws Exception {
        return sum();
    }

    public void run() {
        result = sum();
    }
    private  int sum() {
        return fibo(param);
    }

    private  int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    @Override
    public Integer get() {
        return sum();
    }
}
