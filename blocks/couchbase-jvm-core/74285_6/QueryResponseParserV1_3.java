
package com.couchbase.client.core.endpoint.query.parser;
import com.couchbase.client.core.endpoint.util.ClosingPositionBufProcessor;
import com.couchbase.client.core.endpoint.util.StringClosingPositionBufProcessor;
import com.couchbase.client.core.endpoint.util.WhitespaceSkipper;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import com.couchbase.client.core.utils.UnicastAutoReleaseSubject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import rx.Scheduler;
import rx.subjects.AsyncSubject;

import java.util.concurrent.TimeUnit;

import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextChar;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextCharNotPrefixedBy;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSectionClosingPosition;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSplitPosition;

public class QueryResponseParserV1 extends AbstractQueryResponseParser {
    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(QueryResponseParserV1.class);

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


    private byte queryParsingState = QUERY_STATE_INITIAL;

    private boolean sectionDone = false;

    private boolean sentResponse = false;

    public QueryResponseParserV1(Scheduler scheduler, long ttl) {
        super(scheduler, ttl);
    }

    public void initialize(CouchbaseRequest request, ByteBuf responseContent, ResponseStatus status) {
        this.currentRequest = request;
        this.responseContent = responseContent;
        this.status = status;
        this.initialized = true;
        this.sentResponse = false;
        this.response = null;
        queryParsingState = QUERY_STATE_INITIAL;
    }

    private boolean isEmptySection(int openBracketPos) {
        int nextColon = findNextChar(responseContent, ':');
        return nextColon > -1 && nextColon < openBracketPos;
    }


    public GenericQueryResponse parse(boolean lastChunk) {
        if (this.response == null) {
            handleGenericQueryResponse(lastChunk);
            if (this.response == null) {
                return null;
            }
        }
        parseQueryResponse(lastChunk);
        if (!this.sentResponse && this.response != null) {
            this.sentResponse = true;
            return this.response;
        }
        return null;
    }

    private void handleGenericQueryResponse(boolean lastChunk) {
        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() < MINIMUM_WINDOW_FOR_REQUESTID + MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && !lastChunk) {
            return; //wait for more data
        }

        int startIndex = responseContent.readerIndex();

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_REQUESTID) {
            responseContent.skipBytes(findNextChar(responseContent, ':'));
            responseContent.skipBytes(findNextChar(responseContent, '"') + 1);
            int endOfId = findNextChar(responseContent, '"');
            ByteBuf slice = responseContent.readSlice(endOfId);
            requestId = slice.toString(CHARSET);
        } else {
            return;
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
                    return;
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
            return;
        }

        if (!success) {
            status = ResponseStatus.FAILURE;
        }

        queryRowObservable = UnicastAutoReleaseSubject.create(this.ttl, TimeUnit.MILLISECONDS, this.scheduler);
        queryErrorObservable = UnicastAutoReleaseSubject.create(this.ttl, TimeUnit.MILLISECONDS, this.scheduler);
        queryStatusObservable = AsyncSubject.create();
        queryInfoObservable = UnicastAutoReleaseSubject.create(this.ttl, TimeUnit.MILLISECONDS, this.scheduler);
        querySignatureObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, this.scheduler);

        String rid = clientId == null ? requestId : clientId + " / " + requestId;
        queryRowObservable.withTraceIdentifier("queryRow." + rid);
        queryErrorObservable.withTraceIdentifier("queryError." + rid);
        queryInfoObservable.withTraceIdentifier("queryInfo." + rid);
        querySignatureObservable.withTraceIdentifier("querySignature." + rid);

        this.response = new GenericQueryResponse(
                queryErrorObservable.onBackpressureBuffer().observeOn(scheduler),
                queryRowObservable.onBackpressureBuffer().observeOn(scheduler),
                querySignatureObservable.onBackpressureBuffer().observeOn(scheduler),
                queryStatusObservable.onBackpressureBuffer().observeOn(scheduler),
                queryInfoObservable.onBackpressureBuffer().observeOn(scheduler),
                this.currentRequest,
                this.status, requestId, clientId);
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

    private void finishInfo() {
        queryInfoObservable.onCompleted();
        sectionDone();
        queryParsingState = QUERY_STATE_DONE;
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
}
