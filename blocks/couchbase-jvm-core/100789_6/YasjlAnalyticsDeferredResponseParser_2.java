
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
import rx.Scheduler;
import java.util.concurrent.TimeUnit;

public class YasjlAnalyticsDeferredResponseParser {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(YasjlAnalyticsResponseParser.class);

    private final Scheduler scheduler;

    private final long ttl;

    private final ByteBufJsonParser parser;

    private UnicastAutoReleaseSubject<ByteBuf> queryRowObservable;

    private CouchbaseRequest currentRequest;

    private boolean initialized;

    private GenericAnalyticsResponse response;

    private boolean sentResponse;

    private ByteBuf responseContent;

    private final CoreEnvironment env;

    public YasjlAnalyticsDeferredResponseParser(final Scheduler scheduler, final long ttl, final CoreEnvironment env) {
        this.scheduler = scheduler;
        this.ttl = ttl;
        this.response = null;
        this.env = env;

        JsonPointer[] jsonPointers = {

            new JsonPointer("/-", new JsonPointerCB1() {
                public void call(ByteBuf buf) {
                    if (queryRowObservable != null) {
                        queryRowObservable.onNext(buf);
                        if (response == null) {
                            createResponse();
                            LOGGER.trace("Started receiving results for deferred queries");
                        }
                    }
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
        this.sentResponse = false;
        this.response = null;
        this.responseContent = responseContent;
        this.currentRequest = request;

        queryRowObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);

        parser.initialize(responseContent);
        initialized = true;
    }

    private void createResponse() {
        response = new GenericAnalyticsResponse(
            null,
            queryRowObservable.onBackpressureBuffer(),
            null,
            null,
            null,
            null,
            currentRequest,
            null,
            null,
            null
        );
    }

    public GenericAnalyticsResponse parse() throws Exception {
        try {
            parser.parse();
            responseContent.discardReadBytes();
        } catch (EOFException ex) {
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
        queryRowObservable = null;
        this.initialized = false;
    }
}
