package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public class RequestHeaderAppendFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders header = fullRequest.headers();//获取Netty内置的请求头对象
        header.set("nio","zhoujiapeng");
    }
}
