import run.AsyncTaskRunner;
import run.CallableRunner;
import run.CyclicQueryRunner;
import run.JoinRunner;
import task.Task;

public class Main {
    static Class[] classes ;
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
                System.out.println("result1："+ runner1.get());
                System.out.println("result2："+ runner2.get());
                System.out.println("result2："+ runner3.get());
                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
                System.out.println("-----------------"+ cls.getName()+"-------------------");
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println("                                      ");

        }


    }
    static {
        classes = new Class[]{
                JoinRunner.class,
                CyclicQueryRunner.class,
                CallableRunner.class
        };
    }
}
