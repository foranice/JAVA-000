package main;

import run.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
public class Main {
    static Class[] classes ;
    public static final CountDownLatch latch = new CountDownLatch(3);
    public static CyclicBarrier  barrier = new CyclicBarrier(3, ()->{
        CyclicBarrierRunner.finish = true;
    });

    public static void main(String[] args) {
        for (Class cls:classes
             ) {

            try{
                long start=System.currentTimeMillis();
                AsyncTaskRunner runner1=(AsyncTaskRunner)cls.newInstance();
                AsyncTaskRunner runner2=(AsyncTaskRunner)cls.newInstance();
                AsyncTaskRunner runner3=(AsyncTaskRunner)cls.newInstance();
                runner1.run(36);
                runner2.run(37);
                runner3.run(38);
                System.out.println("-----------------"+ cls.getName()+"-------------------");
                TreeMap<String,AsyncTaskRunner> tasks = new TreeMap<>();
                tasks.put("result1",runner1);
                tasks.put("result2",runner2);
                tasks.put("result3",runner3);
                Map<String,Integer> result = AsyncTaskRunner.getAll(tasks,cls);
                for (Map.Entry<String,Integer> res : result.entrySet()){
                    System.out.println(res.getKey()+":"+res.getValue());
                }
                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
                System.out.println("-----------------"+ cls.getName()+"-------------------");
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println("                                      ");

        }
        System.exit(0);


    }
    static {
        classes = new Class[]{
                CyclicBarrierRunner.class,
                FileRunner.class,
                CountDownLatchRunner.class,
                CompletableFutureRunner.class,
                JoinRunner.class,
                CyclicQueryRunner.class,
                CallableRunner.class,
                DataBaseRunner.class
        };
    }
}
