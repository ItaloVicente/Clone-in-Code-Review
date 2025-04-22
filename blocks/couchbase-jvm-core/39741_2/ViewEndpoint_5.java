package com.couchbase.client.core.endpoint.query;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.AbstractGenericHandler;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.GenericQueryRequest;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import com.couchbase.client.core.message.query.QueryRequest;
import com.lmax.disruptor.RingBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;

import java.util.Queue;

public class QueryHandler extends AbstractGenericHandler<HttpObject, HttpRequest, QueryRequest> {

    private HttpResponse responseHeader;

    private ByteBuf responseContent;

    public QueryHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer) {
        super(endpoint, responseBuffer);
    }

    QueryHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<QueryRequest> queue) {
        super(endpoint, responseBuffer, queue);
    }

    @Override
    protected HttpRequest encodeRequest(final ChannelHandlerContext ctx, final QueryRequest msg) throws Exception {
        FullHttpRequest request;

        if (msg instanceof GenericQueryRequest) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/query");
            ByteBuf query = ctx.alloc().buffer(((GenericQueryRequest) msg).query().length());
            query.writeBytes(((GenericQueryRequest) msg).query().getBytes(CHARSET));
            request.headers().add(HttpHeaders.Names.CONTENT_LENGTH, query.readableBytes());
            request.content().writeBytes(query);
            query.release();
        } else {
            throw new IllegalArgumentException("Unknown incoming QueryRequest type "
                + msg.getClass());
        }

        return request;
    }

    @Override
    protected CouchbaseResponse decodeResponse(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        QueryRequest request = currentRequest();
        CouchbaseResponse response = null;

        if (msg instanceof HttpResponse) {
            responseHeader = (HttpResponse) msg;

            if (responseContent != null) {
                responseContent.clear();
            } else {
                responseContent = ctx.alloc().buffer();
            }
        }

        if (msg instanceof HttpContent) {
            responseContent.writeBytes(((HttpContent) msg).content());

            if (request instanceof GenericQueryRequest) {
                response = handleGenericQueryResponse(ctx, responseContent, msg instanceof LastHttpContent, currentRequest());
            }
        }

        return response;
    }

    private CouchbaseResponse handleGenericQueryResponse(final ChannelHandlerContext ctx, final ByteBuf content,
        final boolean last, final QueryRequest request) {
        ByteBuf returnSlice = ctx.alloc().buffer();
        boolean success = fillResponseBuffer(returnSlice, content);
        if (returnSlice.readableBytes() == 0) {
            return null;
        }

        ResponseStatus status;
        if (success) {
            status = last ? ResponseStatus.SUCCESS : ResponseStatus.CHUNKED;
        } else {
            status = ResponseStatus.FAILURE;
        }
        return new GenericQueryResponse(returnSlice, status, request);
    }

    private boolean fillResponseBuffer(final ByteBuf returnSlice, final ByteBuf content) {
        if (content.readableBytes() < 30) {
            return true;
        }

        if (start) {
            int secondBrace = content.bytesBefore(1, content.readableBytes(), (byte) '{');
            String preamble = content.slice(0, secondBrace).toString(CHARSET);
            if (preamble.contains("error")) {
                error = true;
            }
            content.readerIndex(secondBrace);
            start = false;
        }

        MarkerProcessor processor = new MarkerProcessor();
        content.forEachByte(processor);

        if (processor.marker() > 0) {
            returnSlice.writeBytes(content, content.readerIndex(), processor.marker());
            content.readerIndex(processor.marker());
        }


        return !error;
    }

    private boolean start = true;
    private boolean error = false;

    private static class MarkerProcessor implements ByteBufProcessor {

        private int marker = 0;
        private int counter = 0;
        private int depth = 0;
        private byte open = '{';
        private byte close = '}';
        private byte stringMarker = '"';
        private boolean inString = false;

        @Override
        public boolean process(byte value) throws Exception {
            counter++;
            if (value == stringMarker) {
                inString = !inString;
            }
            if (!inString && value == open) {
                depth++;
            }
            if (!inString && value == close) {
                depth--;
                if (depth == 0) {
                    marker = counter;
                }
            }
            return true;
        }

        public int marker() {
            return marker;
        }
    }
}
