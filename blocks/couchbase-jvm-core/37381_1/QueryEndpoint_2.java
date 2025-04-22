package com.couchbase.client.core.endpoint.query;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.GenericQueryRequest;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import com.couchbase.client.core.message.query.QueryRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class QueryCodec extends MessageToMessageCodec<FullHttpResponse, QueryRequest> {

    private final Queue<Class<?>> queue;

    public QueryCodec() {
        this(new ArrayDeque<Class<?>>());
    }

    public QueryCodec(final Queue<Class<?>> queue) {
        this.queue = queue;
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final QueryRequest msg, final List<Object> out)
        throws Exception {
        HttpRequest request;
        if (msg instanceof GenericQueryRequest) {
            request = handleGenericQueryRequest(ctx, (GenericQueryRequest) msg);
        } else {
            throw new IllegalArgumentException("Unknown Message to encode: " + msg);
        }
        out.add(request);
        queue.offer(msg.getClass());
    }

    private HttpRequest handleGenericQueryRequest(ChannelHandlerContext ctx, GenericQueryRequest msg) {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/query");
        ByteBuf query = Unpooled.copiedBuffer(msg.query(), CharsetUtil.UTF_8);
        request.headers().add(HttpHeaders.Names.CONTENT_LENGTH, query.readableBytes());
        request.content().writeBytes(query);
        return request;
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final FullHttpResponse msg, final List<Object> out)
        throws Exception {
        Class<?> request = queue.poll();
        if (request.equals(GenericQueryRequest.class)) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readValue(msg.content().toString(CharsetUtil.UTF_8), JsonNode.class);
            if (root.has("resultset")) {
                for(JsonNode result : root.path("resultset")) {
                    out.add(new GenericQueryResponse(result.toString(), ResponseStatus.CHUNKED));
                }
                out.add(new GenericQueryResponse(null, ResponseStatus.SUCCESS)); // todo: fixme
            } else if (root.has("error")) {
                out.add(new GenericQueryResponse(root.get("error").toString(), ResponseStatus.FAILURE));
            } else {
                throw new IllegalStateException("Dont know how to deal with that response.");
            }
        }
    }
}
