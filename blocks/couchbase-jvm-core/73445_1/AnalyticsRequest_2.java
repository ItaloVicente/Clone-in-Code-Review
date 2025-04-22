package com.couchbase.client.core.endpoint.analytics;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.AbstractGenericHandler;
import com.couchbase.client.core.endpoint.ResponseStatusConverter;
import com.couchbase.client.core.endpoint.util.ClosingPositionBufProcessor;
import com.couchbase.client.core.endpoint.util.StringClosingPositionBufProcessor;
import com.couchbase.client.core.endpoint.util.WhitespaceSkipper;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.KeepAlive;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.analytics.AnalyticsRequest;
import com.couchbase.client.core.message.analytics.GenericAnalyticsRequest;
import com.couchbase.client.core.message.analytics.GenericAnalyticsResponse;
import com.couchbase.client.core.message.analytics.RawAnalyticsRequest;
import com.couchbase.client.core.message.analytics.RawAnalyticsResponse;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.utils.UnicastAutoReleaseSubject;
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
import rx.Scheduler;
import rx.subjects.AsyncSubject;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextChar;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextCharNotPrefixedBy;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSectionClosingPosition;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSplitPosition;

public class AnalyticsHandler extends AbstractGenericHandler<HttpObject, HttpRequest, AnalyticsRequest> {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(AnalyticsHandler.class);

    protected static final byte QUERY_STATE_INITIAL = 0;
    protected static final byte QUERY_STATE_SIGNATURE = 1;
    protected static final byte QUERY_STATE_ROWS = 2;
    protected static final byte QUERY_STATE_ROWS_RAW = 20;
    protected static final byte QUERY_STATE_ROWS_DECIDE = 29;
    protected static final byte QUERY_STATE_ERROR = 3;
    protected static final byte QUERY_STATE_WARNING = 4;
    protected static final byte QUERY_STATE_STATUS = 5;
    protected static final byte QUERY_STATE_INFO = 6;
    protected static final byte QUERY_STATE_NO_INFO = 7; //alternate case where there's nothing after status
    protected static final byte QUERY_STATE_DONE = 8;

    private static final int MINIMUM_WINDOW_FOR_REQUESTID = 55;

    public static final int MINIMUM_WINDOW_FOR_CLIENTID_TOKEN = 27;

    private HttpResponse responseHeader;

    private ByteBuf responseContent;

    private UnicastAutoReleaseSubject<ByteBuf> queryRowObservable;

    private UnicastAutoReleaseSubject<ByteBuf> querySignatureObservable;

    private UnicastAutoReleaseSubject<ByteBuf> queryErrorObservable;

    private AsyncSubject<String> queryStatusObservable;

    private UnicastAutoReleaseSubject<ByteBuf> queryInfoObservable;

    private byte queryParsingState = QUERY_STATE_INITIAL;

    private boolean sectionDone = false;

    public AnalyticsHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, boolean isTransient,
                            final boolean pipeline) {
        super(endpoint, responseBuffer, isTransient, pipeline);
    }

    AnalyticsHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<AnalyticsRequest> queue,
                     boolean isTransient, final boolean pipeline) {
        super(endpoint, responseBuffer, queue, isTransient, pipeline);
    }

    @Override
    protected HttpRequest encodeRequest(final ChannelHandlerContext ctx, final AnalyticsRequest msg) throws Exception {
        FullHttpRequest request;

        if (msg instanceof GenericAnalyticsRequest) {
            GenericAnalyticsRequest queryRequest = (GenericAnalyticsRequest) msg;
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/query");
            request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
            if (queryRequest.isJsonFormat()) {
                request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
            }
            ByteBuf query = ctx.alloc().buffer(((GenericAnalyticsRequest) msg).query().length());
            query.writeBytes(((GenericAnalyticsRequest) msg).query().getBytes(CHARSET));
            request.headers().add(HttpHeaders.Names.CONTENT_LENGTH, query.readableBytes());
            request.headers().set(HttpHeaders.Names.HOST, remoteHttpHost(ctx));
            request.content().writeBytes(query);
            query.release();
        } else if (msg instanceof KeepAliveRequest) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/admin/ping");
            request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
            request.headers().set(HttpHeaders.Names.HOST, remoteHttpHost(ctx));
            return request;
        } else {
            throw new IllegalArgumentException("Unknown incoming QueryRequest type "
                + msg.getClass());
        }

        addHttpBasicAuth(ctx, request, msg.bucket(), msg.password());
        return request;
    }

    @Override
    protected CouchbaseResponse decodeResponse(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        CouchbaseResponse response = null;

        if (msg instanceof HttpResponse) {
            responseHeader = (HttpResponse) msg;
            if (responseContent != null) {
                responseContent.clear();
            } else {
                responseContent = ctx.alloc().buffer();
            }
        }

        if (currentRequest() instanceof KeepAliveRequest) {
            if (msg instanceof LastHttpContent) {
                response = new KeepAliveResponse(ResponseStatusConverter.fromHttp(responseHeader.getStatus().code()), currentRequest());
                responseContent.clear();
                responseContent.discardReadBytes();
                finishedDecoding();
            }
        } else if (msg instanceof HttpContent) {
            responseContent.writeBytes(((HttpContent) msg).content());
            boolean lastChunk = msg instanceof LastHttpContent;

            if (currentRequest() instanceof RawAnalyticsRequest) {
                response = handleRawAnalyticsResponse(lastChunk, ctx);
            } else if (currentRequest() instanceof GenericAnalyticsRequest) {
                if (queryRowObservable == null) {
                    response = handleGenericAnalyticsResponse(lastChunk);
                    if (response != null) {
                        parseQueryResponse(lastChunk);
                    }
                } else {
                    parseQueryResponse(lastChunk);
                }
            }
        }

        return response;
    }

    private RawAnalyticsResponse handleRawAnalyticsResponse(boolean lastChunk, ChannelHandlerContext ctx) {
        if (!lastChunk) {
            return null;
        }
        ResponseStatus status = ResponseStatusConverter.fromHttp(responseHeader.getStatus().code());
        ByteBuf responseCopy = ctx.alloc().buffer(responseContent.readableBytes(), responseContent.readableBytes());
        responseCopy.writeBytes(responseContent);

        cleanupQueryStates();

        return new RawAnalyticsResponse(status, currentRequest(), responseCopy,
                responseHeader.getStatus().code(),
                responseHeader.getStatus().reasonPhrase());
    }

    private boolean isEmptySection(int openBracketPos) {
        int nextColon = findNextChar(responseContent, ':');
        return nextColon > -1 && nextColon < openBracketPos;
    }

    private CouchbaseResponse handleGenericAnalyticsResponse(boolean lastChunk) {
        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() < MINIMUM_WINDOW_FOR_REQUESTID + MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && !lastChunk) {
            return null; //wait for more data
        }

        int startIndex = responseContent.readerIndex();

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_REQUESTID) {
            responseContent.skipBytes(findNextChar(responseContent, ':'));
            responseContent.skipBytes(findNextChar(responseContent, '"') + 1);
            int endOfId = findNextChar(responseContent, '"');
            ByteBuf slice = responseContent.readSlice(endOfId);
            requestId = slice.toString(CHARSET);
        } else {
            return null;
        }


        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && findNextChar(responseContent, ':') < MINIMUM_WINDOW_FOR_CLIENTID_TOKEN) {
            responseContent.markReaderIndex();
            ByteBuf slice = responseContent.readSlice(findNextChar(responseContent, ':'));
            if (slice.toString(CHARSET).contains("clientContextID")) {
                responseContent.skipBytes(findNextChar(responseContent, '"') + 1); //opening of clientId
                int clientIdSize = findNextCharNotPrefixedBy(responseContent, '"', '\\');
                if (clientIdSize < 0) {
                    responseContent.readerIndex(startIndex);
                    return null;
                }
                clientId = responseContent.readSlice(clientIdSize).toString(CHARSET);
                boolean hasClosingQuote = responseContent.readableBytes() > 0;
                if (hasClosingQuote) {
                    responseContent.skipBytes(1);
                }
                int openingNextToken = findNextChar(responseContent, '"');
                if (openingNextToken > -1) {
                    responseContent.skipBytes(openingNextToken);
                }
            } else {
                responseContent.resetReaderIndex();
            }
        }

        boolean success = true;
        if (responseContent.readableBytes() >= 20) {
            ByteBuf peekForErrors = responseContent.slice(responseContent.readerIndex(), 20);
            if (peekForErrors.toString(CHARSET).contains("errors")) {
                success = false;
            }
        } else {
            responseContent.readerIndex(startIndex);
            return null;
        }

        ResponseStatus status = ResponseStatusConverter.fromHttp(responseHeader.getStatus().code());
        if (!success) {
            status = ResponseStatus.FAILURE;
        }

        Scheduler scheduler = env().scheduler();
        long ttl = env().autoreleaseAfter();
        queryRowObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryErrorObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryStatusObservable = AsyncSubject.create();
        queryInfoObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        querySignatureObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);

        String rid = clientId == null ? requestId : clientId + " / " + requestId;
        queryRowObservable.withTraceIdentifier("queryRow." + rid);
        queryErrorObservable.withTraceIdentifier("queryError." + rid);
        queryInfoObservable.withTraceIdentifier("queryInfo." + rid);
        querySignatureObservable.withTraceIdentifier("querySignature." + rid);

        return new GenericAnalyticsResponse(
                queryErrorObservable.onBackpressureBuffer().observeOn(scheduler),
                queryRowObservable.onBackpressureBuffer().observeOn(scheduler),
                querySignatureObservable.onBackpressureBuffer().observeOn(scheduler),
                queryStatusObservable.onBackpressureBuffer().observeOn(scheduler),
                queryInfoObservable.onBackpressureBuffer().observeOn(scheduler),
                currentRequest(),
                status, requestId, clientId
        );
    }

    private void parseQueryResponse(boolean lastChunk) {
        if (sectionDone || queryParsingState == QUERY_STATE_INITIAL) {
            queryParsingState = transitionToNextToken(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_SIGNATURE) {
            parseQuerySignature(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_ROWS_DECIDE) {
            decideBetweenRawAndObjects(lastChunk);
        }
        if (queryParsingState == QUERY_STATE_ROWS) {
            parseQueryRows(lastChunk);
        } else if (queryParsingState == QUERY_STATE_ROWS_RAW) {
            parseQueryRowsRaw(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_ERROR) {
            parseQueryError(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_WARNING) {
            parseQueryError(lastChunk); //warning are treated the same as errors -> sent to errorObservable
        }

        if (queryParsingState == QUERY_STATE_STATUS) {
            parseQueryStatus(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_INFO) {
            parseQueryInfo(lastChunk);
        } else if (queryParsingState == QUERY_STATE_NO_INFO) {
            finishInfo();
        }

        if (queryParsingState == QUERY_STATE_DONE) {
            sectionDone = lastChunk;
            if (sectionDone) {
                cleanupQueryStates();
            }
        }
    }

    private byte transitionToNextToken(boolean lastChunk) {
        int endNextToken = findNextChar(responseContent, ':');
        if (endNextToken < 0 && !lastChunk) {
            return queryParsingState;
        }

        if (endNextToken < 0 && lastChunk && queryParsingState >= QUERY_STATE_STATUS) {
            return QUERY_STATE_NO_INFO;
        }

        byte newState;
        ByteBuf peekSlice = responseContent.readSlice(endNextToken + 1);
        String peek = peekSlice.toString(CHARSET);
        if (peek.contains("\"signature\":")) {
            newState = QUERY_STATE_SIGNATURE;
        } else if (peek.endsWith("\"results\":")) {
            newState = QUERY_STATE_ROWS_DECIDE;
        } else if (peek.endsWith("\"status\":")) {
            newState = QUERY_STATE_STATUS;
        } else if (peek.endsWith("\"errors\":")) {
            newState = QUERY_STATE_ERROR;
        } else if (peek.endsWith("\"warnings\":")) {
            newState = QUERY_STATE_WARNING;
        } else if (peek.endsWith("\"metrics\":")) {
            newState = QUERY_STATE_INFO;
        } else {
            if (lastChunk) {
                IllegalStateException e = new IllegalStateException("Error parsing query response (in TRANSITION) at \""
                        + peek + "\", enable trace to see response content");
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(responseContent.toString(CHARSET), e);
                }
                throw e;
            } else {
                return queryParsingState;
            }
        }

        sectionDone = false;
        return newState;
    }

    private void decideBetweenRawAndObjects(boolean lastChunk) {
        responseContent.markReaderIndex();
        int openArrayPos = findNextChar(responseContent, '[');
        if (openArrayPos > -1) {
            responseContent.skipBytes(openArrayPos + 1);
        } else {
            responseContent.resetReaderIndex();
            if (lastChunk == true) {
                throw new IllegalStateException("Unable to decide between raw and objects with content " + responseContent.toString(CHARSET));
            }
            return; //more data
        }

        int spaceToSkip = responseContent.forEachByte(new WhitespaceSkipper());
        if (spaceToSkip > -1) {
            responseContent.readerIndex(spaceToSkip);
        } else {
            responseContent.resetReaderIndex();
            return;
        }

        if (responseContent.isReadable()) {
            byte first = responseContent.getByte(responseContent.readerIndex());
            if (first == '{') {
                queryParsingState = QUERY_STATE_ROWS;
            } else if (first == ']') {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
            } else {
                queryParsingState = QUERY_STATE_ROWS_RAW;
            }
        } else {
            responseContent.resetReaderIndex();
        }
    }

    private void sectionDone() {
        this.sectionDone = true;
        responseContent.discardReadBytes();
    }

    private void parseQuerySignature(boolean lastChunk) {
        ByteBufProcessor processor = null;
        int openPos = responseContent.forEachByte(new WhitespaceSkipper()) - responseContent.readerIndex();
        if (openPos < 0) {
            return;
        }
        char openChar = (char) responseContent.getByte(responseContent.readerIndex() + openPos);
        if (openChar == '{') {
            processor = new ClosingPositionBufProcessor('{', '}', true);
        } else if (openChar == '[') {
            processor = new ClosingPositionBufProcessor('[', ']', true);
        } else if (openChar == '"') {
            processor = new StringClosingPositionBufProcessor();
        } //else this should be a scalar, skip processor

        int closePos;
        if (processor != null) {
            closePos = responseContent.forEachByte(processor) - responseContent.readerIndex();
        } else {
            closePos = findNextChar(responseContent, ',') - 1;
        }
        if (closePos > 0) {
            responseContent.skipBytes(openPos);
            int length = closePos - openPos + 1;
            ByteBuf signature = responseContent.readSlice(length);
            querySignatureObservable.onNext(signature.copy());
        } else {
            return;
        }
        sectionDone();
        queryParsingState = transitionToNextToken(lastChunk);
    }

    private void parseQueryRows(boolean lastChunk) {
        while (true) {
            int openBracketPos = findNextChar(responseContent, '{');
            if (isEmptySection(openBracketPos) || (lastChunk && openBracketPos < 0)) {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
                break;
            }

            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
            }

            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
            responseContent.discardSomeReadBytes();
        }
    }

    private void parseQueryRowsRaw(boolean lastChunk) {
        while (responseContent.isReadable()) {
            int splitPos = findSplitPosition(responseContent, ',');
            int arrayEndPos = findSplitPosition(responseContent, ']');

            boolean doSectionDone = false;

            if (splitPos == -1 && arrayEndPos == -1) {
                break;
            } else if (arrayEndPos > 0 && (arrayEndPos < splitPos || splitPos == -1)) {
                splitPos = arrayEndPos;
                doSectionDone = true;
            }

            int length = splitPos - responseContent.readerIndex();
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
            responseContent.skipBytes(1);
            responseContent.discardReadBytes();

            if (doSectionDone) {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
                break;
            }
        }
    }

    private void parseQueryError(boolean lastChunk) {
        while (true) {
            int openBracketPos = findNextChar(responseContent, '{');
            if (isEmptySection(openBracketPos) || (lastChunk && openBracketPos < 0)) {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk); //warnings or status
                break;
            }

            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
            }

            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryErrorObservable.onNext(resultSlice.copy());
        }
    }

    private void parseQueryStatus(boolean lastChunk) {
        querySignatureObservable.onCompleted();
        queryRowObservable.onCompleted();
        queryErrorObservable.onCompleted();

        responseContent.markReaderIndex();
        responseContent.skipBytes(findNextChar(responseContent, '"') + 1);
        int endStatus = findNextChar(responseContent, '"');
        if (endStatus > -1) {
            ByteBuf resultSlice = responseContent.readSlice(endStatus);
            queryStatusObservable.onNext(resultSlice.toString(CHARSET));
            queryStatusObservable.onCompleted();
            sectionDone();
            queryParsingState = transitionToNextToken(lastChunk);
        } else {
            responseContent.resetReaderIndex();
            return; //need more data
        }
    }

    private void parseQueryInfo(boolean last) {
        int openBracketPos = findNextChar(responseContent, '{');
        int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
        if (closeBracketPos == -1) {
            if (last) {
                throw new IllegalStateException("Could not find metrics closing in last chunk");
            } else {
                return; //wait for more data
            }
        }

        int from = responseContent.readerIndex() + openBracketPos;
        int to = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
        queryInfoObservable.onNext(responseContent.slice(from, to).copy());
        responseContent.readerIndex(to + openBracketPos);

        finishInfo();
    }

    private void finishInfo() {
        queryInfoObservable.onCompleted();
        sectionDone();
        queryParsingState = QUERY_STATE_DONE;
    }

    private void cleanupQueryStates() {
        finishedDecoding();
        queryInfoObservable = null;
        queryRowObservable = null;
        queryErrorObservable = null;
        queryStatusObservable = null;
        querySignatureObservable = null;
        queryParsingState = QUERY_STATE_INITIAL;
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        if (queryRowObservable != null) {
            queryRowObservable.onCompleted();
        }
        if (queryInfoObservable != null) {
            queryInfoObservable.onCompleted();
        }
        if (queryErrorObservable != null) {
            queryErrorObservable.onCompleted();
        }
        if (queryStatusObservable != null) {
            queryStatusObservable.onCompleted();
        }
        if (querySignatureObservable != null) {
            querySignatureObservable.onCompleted();
        }
        cleanupQueryStates();
        if (responseContent != null && responseContent.refCnt() > 0) {
            responseContent.release();
        }
        super.handlerRemoved(ctx);
    }

    @Override
    protected CouchbaseRequest createKeepAliveRequest() {
        return new KeepAliveRequest();
    }

    protected static class KeepAliveRequest extends AbstractCouchbaseRequest implements AnalyticsRequest, KeepAlive {
        protected KeepAliveRequest() {
            super(null, null);
        }
    }

    protected static class KeepAliveResponse extends AbstractCouchbaseResponse {
        protected KeepAliveResponse(ResponseStatus status, CouchbaseRequest request) {
            super(status, request);
        }
    }

    @Override
    protected ServiceType serviceType() {
        return ServiceType.QUERY;
    }

    public int getQueryParsingState() {
        return this.queryParsingState;
    }
}
