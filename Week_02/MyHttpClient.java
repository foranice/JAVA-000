import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class MyHttpClient {

    public static void main(String[] args) {
        try{
            OkHttpClient client = new OkHttpClient();
            String url="http://localhost:8801";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String resText= response.body().string();
            System.out.print(resText);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
