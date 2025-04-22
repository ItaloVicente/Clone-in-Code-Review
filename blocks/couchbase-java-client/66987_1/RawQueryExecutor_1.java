package com.couchbase.client.java.util.rawQuerying;

import java.io.IOException;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.RawQueryRequest;
import com.couchbase.client.core.message.query.RawQueryResponse;
import com.couchbase.client.core.message.search.SearchQueryRequest;
import com.couchbase.client.core.message.search.SearchQueryResponse;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.deps.io.netty.util.ReferenceCountUtil;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.FtsConsistencyTimeoutException;
import com.couchbase.client.java.error.FtsMalformedRequestException;
import com.couchbase.client.java.error.QueryExecutionException;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.AbstractFtsQuery;
import com.couchbase.client.java.transcoder.JacksonTransformers;
import com.couchbase.client.java.transcoder.TranscoderUtils;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class AsyncRawQueryExecutor {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(AsyncRawQueryExecutor.class);

    private final String bucket;
    private final String password;
    private final ClusterFacade core;

    public AsyncRawQueryExecutor(String bucket, String password, ClusterFacade core) {
        this.bucket = bucket;
        this.password = password;
        this.core = core;
    }

    public Observable<JsonObject> n1qlToJsonObject(N1qlQuery query) {
        return n1qlToRawCustom(query, new Func1<TranscoderUtils.ByteBufToArray, JsonObject>() {
            @Override
            public JsonObject call(TranscoderUtils.ByteBufToArray converted) {
                try {
                    return JacksonTransformers.MAPPER.readValue(converted.byteArray, converted.offset, converted.length, JsonObject.class);
                } catch (IOException e) {
                    throw new TranscodingException("Unable to deserialize the N1QL raw response", e);
                }
            }
        });
    }

    public Observable<String> n1qlToRawJson(N1qlQuery query) {
        return n1qlToRawCustom(query, new Func1<TranscoderUtils.ByteBufToArray, String>() {
            @Override
            public String call(TranscoderUtils.ByteBufToArray converted) {
                return new String(converted.byteArray, converted.offset, converted.length, CharsetUtil.UTF_8);
            }
        });
    }

    public <T> Observable<T> n1qlToRawCustom(final N1qlQuery query, final Func1<TranscoderUtils.ByteBufToArray, T> deserializer) {
        return Observable.defer(new Func0<Observable<RawQueryResponse>>() {
            @Override
            public Observable<RawQueryResponse> call() {
                RawQueryRequest request = RawQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password);
                return core.<RawQueryResponse>send(request);
            }
        }).map(new Func1<RawQueryResponse, T>() {
            @Override
            public T call(RawQueryResponse response) {
                try {
                    if (response.httpStatusCode() == 200) {
                        return deserializer.call(TranscoderUtils.byteBufToByteArray(response.jsonResponse()));
                    }
                    LOGGER.debug("Unable to perform raw N1QL query (see exception), body was: " +
                            response.jsonResponse().toString( CharsetUtil.UTF_8));
                    throw new QueryExecutionException("Unable to perform raw N1QL query: " + response.httpStatusCode() +
                            " - " + response.httpStatusMsg(), JsonObject.empty());
                } finally {
                    ReferenceCountUtil.release(response.jsonResponse());
                }
            }
        });
    }

    public Observable<JsonObject> ftsToJsonObject(SearchQuery query) {
        return ftsToRawCustom(query, new Func1<String, JsonObject>() {
            @Override
            public JsonObject call(String stringResponse) {
                return JsonObject.fromJson(stringResponse);
            }
        });
    }

    public Observable<String> ftsToRawJson(SearchQuery query) {
        return ftsToRawCustom(query, new Func1<String, String>() {
            @Override
            public String call(String stringResponse) {
                return stringResponse;
            }
        });
    }

    public <T> Observable<T> ftsToRawCustom(final SearchQuery query, final Func1<String, T> deserializer) {
        final String indexName = query.indexName();
        final AbstractFtsQuery queryPart = query.query();

        return Observable.defer(new Func0<Observable<SearchQueryResponse>>() {
            @Override
            public Observable<SearchQueryResponse> call() {
                SearchQueryRequest request = new SearchQueryRequest(indexName, query.export().toString(), bucket, password);
                return core.send(request);
            }
        }).map(new Func1<SearchQueryResponse, T>() {
            @Override
            public T call(SearchQueryResponse response) {
                if (response.status().isSuccess()) {
                    return deserializer.call(response.payload());
                } else if (response.status() == ResponseStatus.INVALID_ARGUMENTS) {
                    throw new FtsMalformedRequestException(response.payload());
                } else if (response.status() == ResponseStatus.FAILURE) {
                    throw new FtsConsistencyTimeoutException();
                } else {
                    throw new CouchbaseException("Could not query search index, " + response.status() + ": " + response.payload());
                }
            }
        });
    }
}
