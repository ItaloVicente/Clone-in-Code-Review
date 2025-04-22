
package com.couchbase.client.core.endpoint.query.parser;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import com.couchbase.client.core.utils.UnicastAutoReleaseSubject;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import rx.Scheduler;
import rx.subjects.AsyncSubject;

import java.nio.charset.Charset;

public abstract class AbstractQueryResponseParser implements QueryResponseParser {

    protected static final Charset CHARSET = CharsetUtil.UTF_8;

    protected ByteBuf responseContent;
    protected UnicastAutoReleaseSubject<ByteBuf> queryRowObservable;

    protected UnicastAutoReleaseSubject<ByteBuf> querySignatureObservable;

    protected UnicastAutoReleaseSubject<ByteBuf> queryErrorObservable;

    protected AsyncSubject<String> queryStatusObservable;

    protected UnicastAutoReleaseSubject<ByteBuf> queryInfoObservable;

    protected CouchbaseRequest currentRequest;

    protected Scheduler scheduler;

    protected long ttl;

    protected ResponseStatus status;

    protected boolean initialized;

    protected GenericQueryResponse response;

    public AbstractQueryResponseParser(Scheduler scheduler, long ttl) {
        this.scheduler = scheduler;
        this.ttl = ttl;
        this.response = null;
    }

    public boolean isInitialized() {
        return this.initialized;
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
        queryInfoObservable = null;
        queryRowObservable = null;
        queryErrorObservable = null;
        queryStatusObservable = null;
        querySignatureObservable = null;
        this.initialized = false;
    }
}
