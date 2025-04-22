package com.couchbase.client.core.endpoint.view;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.AbstractGenericHandler;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.view.GetDesignDocumentRequest;
import com.couchbase.client.core.message.view.GetDesignDocumentResponse;
import com.couchbase.client.core.message.view.ViewQueryRequest;
import com.couchbase.client.core.message.view.ViewQueryResponse;
import com.couchbase.client.core.message.view.ViewRequest;
import com.lmax.disruptor.RingBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
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
import rx.subjects.ReplaySubject;

import java.util.Queue;

public class ViewHandler extends AbstractGenericHandler<HttpObject, HttpRequest, ViewRequest> {

    private static final byte QUERY_STATE_INITIAL = 0;
    private static final byte QUERY_STATE_ROWS = 1;
    private static final byte QUERY_STATE_INFO = 2;
    private static final byte QUERY_STATE_ERROR = 3;
    private static final byte QUERY_STATE_DONE = 4;

    private HttpResponse responseHeader;

    private ByteBuf responseContent;

    private ReplaySubject<ByteBuf> viewRowObservable;

    private ReplaySubject<ByteBuf> viewInfoObservable;

    private byte viewParsingState = QUERY_STATE_INITIAL;

    private ViewRequest previousRequest = null;

    public ViewHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer) {
        super(endpoint, responseBuffer);
    }

    ViewHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<ViewRequest> queue) {
        super(endpoint, responseBuffer, queue);
    }

    @Override
    protected HttpRequest encodeRequest(final ChannelHandlerContext ctx, final ViewRequest msg) throws Exception {
        StringBuilder path = new StringBuilder();

        if (msg instanceof ViewQueryRequest) {
            ViewQueryRequest queryMsg = (ViewQueryRequest) msg;
            path.append("/").append(msg.bucket()).append("/_design/");
            path.append(queryMsg.development() ? "dev_" + queryMsg.design() : queryMsg.design());
            path.append("/_view/").append(queryMsg.view());
            if (queryMsg.query() != null && !queryMsg.query().isEmpty()) {
                path.append("?").append(queryMsg.query());
            }
        } else if (msg instanceof GetDesignDocumentRequest) {
            GetDesignDocumentRequest queryMsg = (GetDesignDocumentRequest) msg;
            path.append("/").append(msg.bucket()).append("/_design/");
            path.append(queryMsg.development() ? "dev_" + queryMsg.name() : queryMsg.name());
        } else {
            throw new IllegalArgumentException("Unknown incoming ViewRequest type "
                + msg.getClass());
        }

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path.toString());
        addAuth(ctx, request, msg.bucket(), msg.password());

        return request;
    }

    @Override
    protected CouchbaseResponse decodeResponse(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        ViewRequest request = currentRequest();
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

            if (currentRequest() == null) {
                currentRequest(previousRequest);
                previousRequest = null;
            }

            if (currentRequest() instanceof ViewQueryRequest) {
                if (viewRowObservable == null) {
                    response = handleViewQueryResponse();
                }

                parseQueryResponse(msg instanceof LastHttpContent);
            }
        }

        if (msg instanceof LastHttpContent) {
            if (request instanceof GetDesignDocumentRequest) {
                response = handleGetDesignDocumentResponse((GetDesignDocumentRequest) request);
            }
        }

        return response;
    }

    private CouchbaseResponse handleGetDesignDocumentResponse(final GetDesignDocumentRequest request) {
        ResponseStatus status = statusFromCode(responseHeader.getStatus().code());
        return new GetDesignDocumentResponse(request.name(), request.development(), responseContent.copy(), status,
            request);
    }

    private CouchbaseResponse handleViewQueryResponse() {
        ResponseStatus status = statusFromCode(responseHeader.getStatus().code());
        viewRowObservable = ReplaySubject.create();
        viewInfoObservable = ReplaySubject.create();
        previousRequest = currentRequest();
        return new ViewQueryResponse(viewRowObservable, viewInfoObservable, status, currentRequest());
    }

    private void parseQueryResponse(boolean last) {
        if (viewParsingState == QUERY_STATE_INITIAL) {
            parseViewInitial();
        }

        if (viewParsingState == QUERY_STATE_INFO) {
            parseViewInfo();
        }

        if (viewParsingState == QUERY_STATE_ERROR) {
            parseViewError(last);
        }

        if (viewParsingState == QUERY_STATE_ROWS) {
            parseViewRows(last);
        }

        if (viewParsingState == QUERY_STATE_DONE) {
            cleanupViewStates();
        }
    }

    private void cleanupViewStates() {
        viewInfoObservable = null;
        viewRowObservable = null;
        viewParsingState = QUERY_STATE_INITIAL;
    }

    private void parseViewInitial() {
        switch(responseHeader.getStatus().code()) {
            case 200:
                viewParsingState = QUERY_STATE_INFO;
                break;
            default:
                viewRowObservable.onCompleted();
                viewParsingState = QUERY_STATE_ERROR;
        }
    }

    private void parseViewError(boolean last) {
        if (!last) {
            return;
        }

        viewInfoObservable.onNext(responseContent.copy());
        viewInfoObservable.onCompleted();
        viewParsingState = QUERY_STATE_DONE;
    }

    private void parseViewInfo() {
        int rowsStart = -1;
        for (int i = responseContent.readerIndex(); i < responseContent.writerIndex(); i++) {
            byte curr = responseContent.getByte(i);
            byte f1 = responseContent.getByte(i+1);
            byte f2 = responseContent.getByte(i+2);

            if (curr == '"' && f1 == 'r' && f2 == 'o') {
                rowsStart = i;
                break;
            }
        }

        if (rowsStart == -1) {
            return;
        }

        ByteBuf info = responseContent.readBytes(rowsStart - responseContent.readerIndex());
        int closingPointer = info.forEachByteDesc(new ByteBufProcessor() {
            @Override
            public boolean process(byte value) throws Exception {
                return value != ',';
            }
        });

        info.setByte(closingPointer, '}');
        viewInfoObservable.onNext(info);
        viewInfoObservable.onCompleted();
        viewParsingState = QUERY_STATE_ROWS;
    }

    private void parseViewRows(boolean last) {
        while(true) {
            int openBracketPos = responseContent.bytesBefore((byte) '{');
            int closeBracketPos = -1;
            int openBrackets = 0;
            for (int i = responseContent.readerIndex(); i <= responseContent.writerIndex(); i++) {
                byte current = responseContent.getByte(i);
                if (current == '{') {
                    openBrackets++;
                } else if (current == '}' && openBrackets > 0) {
                    openBrackets--;
                    if (openBrackets == 0) {
                        closeBracketPos = i;
                        break;
                    }
                }
            }

            if (closeBracketPos == -1) {
                break;
            }

            int from = responseContent.readerIndex() + openBracketPos;
            int to = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            viewRowObservable.onNext(responseContent.slice(from, to).copy());
            responseContent.readerIndex(closeBracketPos);
        }

        responseContent.discardReadBytes();
        if (last) {
            viewRowObservable.onCompleted();
            viewParsingState = QUERY_STATE_DONE;
        }
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
        if (viewRowObservable != null) {
            viewRowObservable.onCompleted();
        }
        if (viewInfoObservable != null) {
            viewInfoObservable.onCompleted();
        }
        cleanupViewStates();
        super.handlerRemoved(ctx);
    }
}
