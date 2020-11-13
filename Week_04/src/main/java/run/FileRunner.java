package run;

import helper.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FileRunner implements AsyncTaskRunner{
    static AtomicInteger sequence = new AtomicInteger(1);
    int id = 0;
    private static String fileName="data.txt";
    private FileTask task;
    private Thread thread;
    public FileRunner(){
        this.id = sequence.getAndIncrement();

    }
    @Override
    public void run(int param) {
        task = new FileTask(param,id);
        thread = new Thread(task);
        thread.start();
    }

    @Override
    public Integer get() {
        while(true){
            Map<Integer,Integer> resultMap = readResult();
            if(resultMap.containsKey(id)){
                return resultMap.get(id);
            }
            try{
                Thread.sleep(10);
            }
            catch ( Exception ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public Map<String, Integer> getAll(Map<String, AsyncTaskRunner> tasks) {
        while(true){
            Map<Integer,Integer> resultMap = readResult();
            if(resultMap.size()==tasks.size()){
                Map<String,Integer> result = new HashMap<>();
                for (Map.Entry<String,AsyncTaskRunner> task : tasks.entrySet()) {
                    FileRunner runner = (FileRunner)task.getValue();
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
    private Map<Integer,Integer> readResult(){

        Map<Integer,Integer> resultMap = new HashMap<>();
        String fileContent = ReadFile();
        if(fileContent.isEmpty()){
            return resultMap;
        }
        String[] lines =fileContent.split(System.lineSeparator());
        for (String line :lines){
            String[] data = line.split(",");
            resultMap.put(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
        }
        return resultMap;
    }
    private static  void WriteFile(String content,Boolean append){
        File file = new File(fileName);
        try( FileOutputStream fileOutputStream = new FileOutputStream(file,append)){
            fileOutputStream.write(content.getBytes());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
    private static String ReadFile(){
        File file = new File(fileName);
        byte[] buffer = new byte[1024];
        try( FileInputStream fileInputStream = new FileInputStream(file)){
            int len = fileInputStream.read(buffer);
            if(len <= 0){
                return "";
            }
            String res =new String(buffer,0,len);
            return res;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }
    static {
        WriteFile("",false);
    }
    class FileTask extends Task {
        int runner_id = 0;
        FileTask(int param,int id){
            super(param);
            runner_id = id;
        }
        @Override
        public void run() {
            super.run();
            String content = runner_id+"," + this.getResult()+System.lineSeparator();
            WriteFile(content,true);
        }

    }
}
