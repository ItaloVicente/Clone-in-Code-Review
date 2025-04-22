
package com.couchbase.client.java.util;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.analytics.AnalyticsQuery;
import com.couchbase.client.java.analytics.AsyncAnalyticsQueryResult;
import com.couchbase.client.java.analytics.AsyncAnalyticsQueryRow;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TemporaryFailureException;
import com.couchbase.client.java.util.retry.RetryBuilder;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public enum AnalyticsIngester {
    ;

    private static final Func1<JsonObject, String> DEFAULT_ID_GENERATOR = new Func1<JsonObject, String>() {
        @Override
        public String call(JsonObject jsonObject) {
            return UUID.randomUUID().toString();
        }
    };

    public static Completable ingest(final Bucket bucket, final AnalyticsQuery query) {
        return ingest(bucket, query, null);
    }

    public static Completable ingest(final Bucket bucket, final AnalyticsQuery query, final IngestOptions options) {
        final IngestOptions opts = options == null ? IngestOptions.ingestOptions() : options;

        if (opts.ingestMethod == IngestMethod.REPLACE && opts.idGenerator.equals(DEFAULT_ID_GENERATOR)) {
            throw new IllegalArgumentException("IngestMethod.REPLACE does not work with the default ID generator " +
                    "which only creates new UUIDs and will make every replace operation fail. Please create " +
                    "your own ID Generator!");
        }

        final long kvTimeout = opts.kvTimeout > 0
                ? opts.kvTimeout
                : bucket.environment().kvTimeout();
        final long anTimeout = opts.analyticsTimeout > 0
                ? opts.analyticsTimeout
                : bucket.environment().analyticsTimeout();

        return bucket
            .async()
            .query(query)
            .timeout(anTimeout, TimeUnit.MILLISECONDS)
            .flatMap(new Func1<AsyncAnalyticsQueryResult, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(AsyncAnalyticsQueryResult result) {
                    Observable<RowWithError> errors = result.errors().map(new Func1<JsonObject, RowWithError>() {
                        @Override
                        public RowWithError call(JsonObject error) {
                            return new RowWithError(null, error);
                        }
                    });

                    Observable<RowWithError> rows = result.rows().map(new Func1<AsyncAnalyticsQueryRow, RowWithError>() {
                        @Override
                        public RowWithError call(AsyncAnalyticsQueryRow row) {
                            return new RowWithError(row, null);
                        }
                    });

                    return Observable
                        .merge(rows, errors)
                        .map(new Func1<RowWithError, RowWithError>() {
                            @Override
                            public RowWithError call(RowWithError rwe) {
                                if (rwe.error != null) {
                                    throw new CouchbaseException(rwe.error.toString());
                                }
                                return rwe;
                            }
                        })
                        .flatMap(new Func1<RowWithError, Observable<JsonDocument>>() {
                            @Override
                            public Observable<JsonDocument> call(RowWithError rwe) {
                                JsonObject data = opts.dataConverter.call(rwe.row.value());
                                String id = opts.idGenerator.call(data);
                                JsonDocument doc = JsonDocument.create(id, data);

                                Observable<JsonDocument> result;
                                switch (opts.ingestMethod) {
                                    case INSERT:
                                        result = bucket.async().insert(doc);
                                        break;
                                    case UPSERT:
                                        result = bucket.async().upsert(doc);
                                        break;
                                    case REPLACE:
                                        result = bucket.async().replace(doc);
                                        break;
                                    default:
                                        return Observable.error(
                                            new UnsupportedOperationException("Unsupported ingest method")
                                        );
                                }
                                result = result.timeout(kvTimeout, TimeUnit.MILLISECONDS);
                                if (opts.retryBuilder != null) {
                                    result = result.retryWhen(opts.retryBuilder.build());
                                }
                                if (opts.ignoreIngestError) {
                                    result = result.onErrorResumeNext(Observable.<JsonDocument>empty());
                                }
                                return result;
                            }
                        });
                }
            })
            .last()
            .toCompletable();
    }

    public static class IngestOptions {

        private IngestOptions() {}

        long analyticsTimeout = 0;
        long kvTimeout = 0;
        IngestMethod ingestMethod = IngestMethod.UPSERT;
        boolean ignoreIngestError = false;
        Func1<JsonObject, JsonObject> dataConverter = new Func1<JsonObject, JsonObject>() {
            @Override
            public JsonObject call(JsonObject in) {
                return in;
            }
        };
        Func1<JsonObject, String> idGenerator = DEFAULT_ID_GENERATOR;
        RetryBuilder retryBuilder = RetryBuilder
            .anyOf(BackpressureException.class, TemporaryFailureException.class)
            .max(10)
            .delay(Delay.exponential(TimeUnit.MILLISECONDS, 500, 2));

        public static IngestOptions ingestOptions() {
            return new IngestOptions();
        }

        public IngestOptions analyticsTimeout(final long timeout, final TimeUnit timeUnit) {
            this.analyticsTimeout = timeUnit.toMillis(timeout);
            return this;
        }

        public IngestOptions kvTimeout(final long timeout, final TimeUnit timeUnit) {
            this.kvTimeout = timeUnit.toMillis(timeout);
            return this;
        }

        public IngestOptions ingestMethod(final IngestMethod ingestMethod) {
            this.ingestMethod = ingestMethod;
            return this;
        }

        public IngestOptions ignoreIngestError(final boolean ignoreIngestError) {
            this.ignoreIngestError = ignoreIngestError;
            return this;
        }

        public IngestOptions retryBuilder(final RetryBuilder retryBuilder) {
            this.retryBuilder = retryBuilder;
            return this;
        }

        public IngestOptions idGenerator(final Func1<JsonObject, String> idGenerator) {
            this.idGenerator = idGenerator;
            return this;
        }

        public IngestOptions dataConverter(final Func1<JsonObject, JsonObject> dataConverter) {
            this.dataConverter = dataConverter;
            return this;
        }

    }

    public enum IngestMethod {
        INSERT,
        UPSERT,
        REPLACE
    }

    private static class RowWithError {
        private final AsyncAnalyticsQueryRow row;
        private final JsonObject error;

        RowWithError(final AsyncAnalyticsQueryRow row, final JsonObject error) {
            this.row = row;
            this.error = error;
        }
    }

}
