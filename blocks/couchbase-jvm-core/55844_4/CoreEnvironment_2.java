package com.couchbase.client.core.endpoint.search;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.AbstractGenericHandler;
import com.couchbase.client.core.endpoint.ResponseStatusConverter;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.search.GetSearchIndexRequest;
import com.couchbase.client.core.message.search.GetSearchIndexResponse;
import com.couchbase.client.core.message.search.InsertSearchIndexRequest;
import com.couchbase.client.core.message.search.InsertSearchIndexResponse;
import com.couchbase.client.core.message.search.RemoveSearchIndexRequest;
import com.couchbase.client.core.message.search.RemoveSearchIndexResponse;
import com.couchbase.client.core.message.search.SearchQueryRequest;
import com.couchbase.client.core.message.search.SearchQueryResponse;
import com.couchbase.client.core.message.search.SearchRequest;
import com.couchbase.client.core.service.ServiceType;
import com.lmax.disruptor.EventSink;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import io.netty.util.CharsetUtil;

public class SearchHandler extends AbstractGenericHandler<HttpObject, HttpRequest, SearchRequest> {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(SearchHandler.class);
    private HttpResponse responseHeader;

    private ByteBuf responseContent;

    public SearchHandler(AbstractEndpoint endpoint, EventSink<ResponseEvent> responseBuffer, boolean isTransient) {
        super(endpoint, responseBuffer, isTransient);
    }

    @Override
    protected HttpRequest encodeRequest(ChannelHandlerContext ctx, SearchRequest msg) throws Exception {
        HttpMethod httpMethod = HttpMethod.GET;
        if (msg instanceof InsertSearchIndexRequest) {
            httpMethod = HttpMethod.PUT;
        } else if (msg instanceof RemoveSearchIndexRequest) {
            httpMethod = HttpMethod.DELETE;
        } else if (msg instanceof SearchQueryRequest) {
            httpMethod = HttpMethod.POST;
        }

        ByteBuf content;
        if (msg instanceof InsertSearchIndexRequest) {
            content = Unpooled.copiedBuffer(((InsertSearchIndexRequest) msg).payload(), CharsetUtil.UTF_8);
        } else if (msg instanceof SearchQueryRequest) {
            content = Unpooled.copiedBuffer(((SearchQueryRequest) msg).payload(), CharsetUtil.UTF_8);
        } else {
            content = Unpooled.EMPTY_BUFFER;
        }

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path(), content);
        request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
        if (msg instanceof InsertSearchIndexRequest || msg instanceof SearchQueryRequest) {
            request.headers().set(HttpHeaders.Names.ACCEPT, "*/*");
            request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        }
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());

        return request;
    }

    @Override
    protected CouchbaseResponse decodeResponse(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        SearchRequest request = currentRequest();
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
        }

        if (msg instanceof LastHttpContent) {
            ResponseStatus status = ResponseStatusConverter.fromHttp(responseHeader.getStatus().code());
            String body = responseContent.readableBytes() > 0
                    ? responseContent.toString(CHARSET) : responseHeader.getStatus().reasonPhrase();

            if (request instanceof InsertSearchIndexRequest) {
                response = new InsertSearchIndexResponse(body, status);
            } else if (request instanceof GetSearchIndexRequest) {
                response = new GetSearchIndexResponse(body, status);
            } else if (request instanceof RemoveSearchIndexRequest) {
                response = new RemoveSearchIndexResponse(body, status);
            } else if (request instanceof SearchQueryRequest) {
                response = new SearchQueryResponse(body, status);
            }

            finishedDecoding();
        }
        return response;
    }

    @Override
    protected ServiceType serviceType() {
        return ServiceType.SEARCH;
    }
}
