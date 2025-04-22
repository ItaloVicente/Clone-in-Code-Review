
package com.couchbase.client.core.endpoint.analytics.parser;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

import com.couchbase.client.core.message.analytics.GenericAnalyticsResponse;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import com.couchbase.client.core.utils.UnicastAutoReleaseSubject;
import com.couchbase.client.core.utils.yasjl.ByteBufJsonParser;
import com.couchbase.client.core.utils.yasjl.Callbacks.JsonPointerCB1;
import com.couchbase.client.core.utils.yasjl.JsonPointer;
import java.io.EOFException;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import rx.Scheduler;
import rx.subjects.AsyncSubject;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class YasjlAnalyticsResponseParser {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(YasjlAnalyticsResponseParser.class);

    private static final Charset CHARSET = CharsetUtil.UTF_8;

    private final Scheduler scheduler;

    private final long ttl;

    private final ByteBufJsonParser parser;

    private UnicastAutoReleaseSubject<ByteBuf> queryRowObservable;

    private UnicastAutoReleaseSubject<ByteBuf> querySignatureObservable;

    private UnicastAutoReleaseSubject<ByteBuf> queryErrorObservable;

    private AsyncSubject<String> queryStatusObservable;

    private UnicastAutoReleaseSubject<ByteBuf> queryInfoObservable;

    private UnicastAutoReleaseSubject<ByteBuf> queryProfileInfoObservable;

    private String handle;

    private CouchbaseRequest currentRequest;

    private ResponseStatus status;

    private boolean initialized;

    private GenericAnalyticsResponse response;

    private String requestID;

    private String clientContextID;

    private boolean sentResponse;

    private ByteBuf responseContent;

    private final CoreEnvironment env;

    public YasjlAnalyticsResponseParser(final Scheduler scheduler, final long ttl, final CoreEnvironment env) {
        this.scheduler = scheduler;
        this.ttl = ttl;
        this.response = null;
        this.env = env;

        JsonPointer[] jsonPointers = {
                new JsonPointer("/requestID", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        requestID = buf.toString(CHARSET);
                        requestID = requestID.substring(1, requestID.length() - 1);
                        buf.release();
                        if (queryRowObservable != null) {
                            queryRowObservable.withTraceIdentifier("queryRow." + requestID);
                        }
                        if (queryErrorObservable != null) {
                            queryErrorObservable.withTraceIdentifier("queryError." + requestID);
                        }
                        if (queryInfoObservable != null) {
                            queryInfoObservable.withTraceIdentifier("queryInfo." + requestID);
                        }
                        if (querySignatureObservable != null) {
                            querySignatureObservable.withTraceIdentifier("querySignature." + requestID);
                        }
                        if (queryProfileInfoObservable != null) {
                            queryProfileInfoObservable.withTraceIdentifier("queryProfileInfo." + requestID);
                        }
                    }
                }),
                new JsonPointer("/clientContextID", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        clientContextID = buf.toString(CHARSET);
                        clientContextID = clientContextID.substring(1, clientContextID.length() - 1);
                        buf.release();
                    }
                }),
                new JsonPointer("/signature", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (querySignatureObservable != null) {
                            querySignatureObservable.onNext(buf);
                        }
                    }
                }),
                new JsonPointer("/status", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryStatusObservable != null) {
                            String statusStr = buf.toString(CHARSET);
                            buf.release();

                            statusStr = statusStr.substring(1, statusStr.length() - 1);
                            if (!statusStr.equals("success")) {
                                status = ResponseStatus.FAILURE;
                            }
                            queryStatusObservable.onNext(statusStr);

                            if (!sentResponse) {
                                createResponse();
                                LOGGER.trace("Received status for requestId {}", requestID);
                            }
                        }
                    }
                }),
                new JsonPointer("/results/-", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryRowObservable != null) {
                            queryRowObservable.onNext(buf);
                            if (response == null) {
                                createResponse();
                                LOGGER.trace("Started receiving results for requestId {}", requestID);
                            }
                        }
                    }
                }),
                new JsonPointer("/errors/-", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryErrorObservable != null) {
                            queryErrorObservable.onNext(buf);
                            if (response == null) {
                                createResponse();
                                LOGGER.trace("Started receiving errors for requestId {}", requestID);
                            }
                        }
                    }
                }),
                new JsonPointer("/warnings/-", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryErrorObservable != null) {
                            queryErrorObservable.onNext(buf);
                            if (response == null) {
                                createResponse();
                                LOGGER.trace("Started receiving warnings for requestId {}", requestID);
                            }
                        }
                    }
                }),
                new JsonPointer("/profile", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryProfileInfoObservable != null) {
                            queryProfileInfoObservable.onNext(buf);
                        }
                    }
                }),
                new JsonPointer("/metrics", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryInfoObservable != null) {
                            queryInfoObservable.onNext(buf);
                        }

                        if (currentRequest.span() != null) {
                            if (env.operationTracingEnabled()) {
                                env.tracer().scopeManager()
                                        .activate(response.request().span(), true)
                                        .close();
                            }
                        }
                    }
                }),
                new JsonPointer("/handle", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        handle = buf.toString(CHARSET);
                        buf.release();
                    }
                }),
        };
        this.parser = new ByteBufJsonParser(jsonPointers);
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void initialize(final ByteBuf responseContent, final ResponseStatus responseStatus,
                           final CouchbaseRequest request) {
        this.requestID = "";
        this.clientContextID = ""; //initialize to empty string instead of null as it is optional on the wire
        this.handle = "";
        this.sentResponse = false;
        this.response = null;
        this.status = responseStatus;
        this.responseContent = responseContent;
        this.currentRequest = request;

        queryRowObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryErrorObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryStatusObservable = AsyncSubject.create();
        queryInfoObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        querySignatureObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryProfileInfoObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);

        parser.initialize(responseContent);
        initialized = true;
    }

    private void createResponse() {
        response = new GenericAnalyticsResponse(
                queryErrorObservable.onBackpressureBuffer(),
                queryRowObservable.onBackpressureBuffer(),
                querySignatureObservable.onBackpressureBuffer(),
                queryStatusObservable.onBackpressureBuffer(),
                queryInfoObservable.onBackpressureBuffer(),
                handle,
                currentRequest,
                status,
                requestID,
                clientContextID
        );
    }

    public GenericAnalyticsResponse parse() throws Exception {
        try {
            parser.parse();
            responseContent.discardReadBytes();
            LOGGER.trace("Received last chunk and completed parsing for requestId {}", requestID);
        } catch (EOFException ex) {
            LOGGER.trace("Still expecting more data for requestId {}", requestID);
        }

        if (!this.sentResponse && this.response != null) {
            this.sentResponse = true;
            return this.response;
        }

        return null;
    }

    public void finishParsingAndReset() {
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
        if (queryProfileInfoObservable != null) {
            queryProfileInfoObservable.onCompleted();
        }
        queryInfoObservable = null;
        queryRowObservable = null;
        queryErrorObservable = null;
        queryStatusObservable = null;
        querySignatureObservable = null;
        queryProfileInfoObservable = null;
        this.initialized = false;
    }
}
