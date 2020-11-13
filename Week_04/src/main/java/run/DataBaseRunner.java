package run;

import main.Main;
import task.Task;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
public class DataBaseRunner implements AsyncTaskRunner{
    public static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    public static final String USER = "sa";
    public static final String PASSWORD = "";
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private Thread thread;
    private Task task;
    static AtomicInteger sequence = new AtomicInteger(1);
    int id = 0;
    public DataBaseRunner(){
        this.id = sequence.getAndIncrement();

    }
    public void run(int param) {
        try{
            task = new DataBaseTask(param,id);
            thread = new Thread(task);
            thread.start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public Integer get() {
        return null;
    }

    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            while(true){
                Map<Integer,Integer> resultMap = readResult(conn);
                if(resultMap.size()==tasks.size()){
                    Map<String,Integer> result = new HashMap<>();
                    for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
                        DataBaseRunner runner = (DataBaseRunner)task.getValue();
                        result.put(task.getKey(),resultMap.get(runner.id));
                    }
                    return result;
                }
                try{
                    Thread.sleep(10);
                }
                catch ( Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;

    }
    private Map<Integer,Integer> readResult(Connection conn) throws SQLException {
        Map<Integer,Integer> resultMap = new HashMap<>();
        String query="select * from test";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            resultMap.put(rs.getInt(1),rs.getInt(2));
        }
        return resultMap;

    }
    static {
        try{
            Class.forName(DRIVER_CLASS);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            Class.forName(DRIVER_CLASS);
            String create = "create table test(id int ,result int); ";
            PreparedStatement ps = conn.prepareStatement(create);
            ps.execute();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    class DataBaseTask extends Task{
        int runner_id = 0;
        DataBaseTask(int param,int id){
            super(param);
            runner_id = id;
        }
        @Override
        public void run() {
            super.run();
            try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
                String insert = "insert into test (id,result) values (?,?)";
                PreparedStatement ps = conn.prepareStatement(insert);
                ps.setInt(1,runner_id);
                ps.setInt(2,this.getResult());
                ps.execute();

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}

