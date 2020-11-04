package io.github.kimmking.gateway.outbound.okhttp;

import io.github.kimmking.gateway.outbound.OutBoundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler implements OutBoundHandler {
    private String backendUrl;
    public OkhttpOutboundHandler(String backendUrl){
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
        //


    }
    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        OkHttpClient okHttpClient = new OkHttpClient();
         Request.Builder builder= new Request.Builder()
                .url(this.backendUrl);
        for (Map.Entry<String,String> header:fullRequest.headers().entries()
             ) {
            builder.addHeader(header.getKey(),header.getValue());

        }
        Request request =builder.build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                ctx.close();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                byte[] body=response.body().bytes();
                FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
                res.headers().set("Content-Type", "text/html");
                res.headers().setInt("Content-Length", Integer.parseInt(response.header("Content-Length")));
                if (fullRequest != null) {
                    if (!HttpUtil.isKeepAlive(fullRequest)) {
                        ctx.write(res).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        ctx.write(res);
                    }
                }
                ctx.flush();
            }
        });
    }
}
