package com.couchbase.client.core.endpoint.config;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.AbstractGenericHandler;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.config.BucketConfigRequest;
import com.couchbase.client.core.message.config.BucketConfigResponse;
import com.couchbase.client.core.message.config.BucketStreamingRequest;
import com.couchbase.client.core.message.config.BucketStreamingResponse;
import com.couchbase.client.core.message.config.ConfigRequest;
import com.couchbase.client.core.message.config.FlushRequest;
import com.couchbase.client.core.message.config.FlushResponse;
import com.couchbase.client.core.message.config.ListDesignDocumentResponse;
import com.couchbase.client.core.message.config.ListDesignDocumentsRequest;
import com.lmax.disruptor.RingBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import rx.subjects.BehaviorSubject;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;


public class ConfigHandler extends AbstractGenericHandler<HttpObject, HttpRequest, ConfigRequest> {

    private HttpResponse responseHeader;

    private ByteBuf responseContent;

    private BehaviorSubject<String> streamingConfigObservable;

    private ConfigRequest previousRequest = null;

    public ConfigHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer) {
        super(endpoint, responseBuffer);
    }

    ConfigHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<ConfigRequest> queue) {
        super(endpoint, responseBuffer, queue);
    }

    @Override
    protected HttpRequest encodeRequest(final ChannelHandlerContext ctx, final ConfigRequest msg) throws Exception {
        HttpMethod httpMethod;

        if (msg instanceof BucketConfigRequest) {
            httpMethod = HttpMethod.GET;
        } else if (msg instanceof BucketStreamingRequest) {
            httpMethod = HttpMethod.GET;
        } else if(msg instanceof FlushRequest) {
            httpMethod = HttpMethod.POST;
        } else if (msg instanceof ListDesignDocumentsRequest) {
            httpMethod = HttpMethod.GET;
        } else {
            throw new IllegalArgumentException("Unknown incoming ConfigRequest type "
                + msg.getClass());
        }

        HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path());
        addAuth(ctx, request, msg.bucket(), msg.password());

        return request;
    }

    private static void addAuth(final ChannelHandlerContext ctx, final HttpRequest request, final String user,
        final String password) {
        ByteBuf raw = ctx.alloc().buffer(user.length() + password.length() + 1);
        raw.writeBytes((user + ":" + password).getBytes(CHARSET));
        ByteBuf encoded = Base64.encode(raw);
        request.headers().add(HttpHeaders.Names.AUTHORIZATION, "Basic " + encoded.toString(CHARSET));
        encoded.release();
        raw.release();
    }

    @Override
    protected CouchbaseResponse decodeResponse(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        ConfigRequest request = currentRequest();
        CouchbaseResponse response = null;

        if (msg instanceof HttpResponse) {
            responseHeader = (HttpResponse) msg;

            if (request instanceof BucketStreamingRequest) {
                response = handleBucketStreamingResponse(ctx, responseHeader);
            }

            if (responseContent != null) {
                responseContent.clear();
            } else {
                responseContent = ctx.alloc().buffer();
            }
        }

        if (msg instanceof HttpContent) {
            responseContent.writeBytes(((HttpContent) msg).content());
            if (streamingConfigObservable != null) {
                if (currentRequest() == null) {
                    currentRequest(previousRequest);
                    previousRequest = null;
                }
                maybePushConfigChunk();
            }
        }

        if (msg instanceof LastHttpContent) {
            if (request instanceof BucketStreamingRequest) {
                if (streamingConfigObservable != null) {
                    streamingConfigObservable.onCompleted();
                    streamingConfigObservable = null;
                }
                return null;
            }

            ResponseStatus status = statusFromCode(responseHeader.getStatus().code());
            String body = responseContent.readableBytes() > 0
                ? responseContent.toString(CHARSET) : responseHeader.getStatus().reasonPhrase();

            if (request instanceof BucketConfigRequest) {
                response = new BucketConfigResponse(body, status);
            } else if (request instanceof ListDesignDocumentsRequest) {
                response = new ListDesignDocumentResponse(body, status, request);
            } else if (request instanceof FlushRequest) {
                boolean done = responseHeader.getStatus().code() != 201;
                response = new FlushResponse(done, body, status);
            }
        }

        return response;
    }

    private CouchbaseResponse handleBucketStreamingResponse(final ChannelHandlerContext ctx, final HttpResponse header) {
        SocketAddress addr = ctx.channel().remoteAddress();
        String host = addr instanceof InetSocketAddress ? ((InetSocketAddress) addr).getHostName() : addr.toString();
        ResponseStatus status = statusFromCode(header.getStatus().code());
        if (status.isSuccess()) {
            streamingConfigObservable = BehaviorSubject.create();
        }
        previousRequest = currentRequest();
        return new BucketStreamingResponse(streamingConfigObservable, host, status, currentRequest());
    }

    private void maybePushConfigChunk() {
        String currentChunk = responseContent.toString(CHARSET);

        int separatorIndex = currentChunk.indexOf("\n\n\n\n");
        if (separatorIndex > 0) {
            String content = currentChunk.substring(0, separatorIndex);
            streamingConfigObservable.onNext(content.trim());
            responseContent.clear();
            responseContent.writeBytes(currentChunk.substring(separatorIndex + 4).getBytes(CHARSET));
        }
    }

    private static ResponseStatus statusFromCode(int code) {
        ResponseStatus status;
        switch(code) {
            case 200:
            case 201:
                status = ResponseStatus.SUCCESS;
                break;
            case 404:
                status = ResponseStatus.NOT_EXISTS;
                break;
            default:
                status = ResponseStatus.FAILURE;
        }
        return status;
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        if (streamingConfigObservable != null) {
            streamingConfigObservable.onCompleted();
        }
        super.handlerRemoved(ctx);
    }

}
