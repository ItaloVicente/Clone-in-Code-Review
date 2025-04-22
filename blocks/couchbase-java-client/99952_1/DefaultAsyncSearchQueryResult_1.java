package com.couchbase.client.java.search.core;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.search.SearchQueryRequest;
import com.couchbase.client.core.message.search.SearchQueryResponse;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.bucket.api.Utils;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.error.CannotRetryException;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.result.AsyncSearchQueryResult;
import com.couchbase.client.java.search.result.impl.DefaultAsyncSearchQueryResult;
import com.couchbase.client.java.util.retry.RetryBuilder;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action4;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

import static com.couchbase.client.java.bucket.api.Utils.applyTimeout;
import static com.couchbase.client.java.util.OnSubscribeDeferAndWatch.deferAndWatch;

public class SearchQueryExecutor {

    private static CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(SearchQueryExecutor.class);

    private static final int HTTP_TOO_MANY_REQUESTS = 429;

    private static final int HTTP_PRECONDITION_FAILED = 421;

    private final CouchbaseEnvironment environment;
    private final ClusterFacade core;
    private final String bucket;
    private final String username;
    private final String password;

    public SearchQueryExecutor(final CouchbaseEnvironment environment, final ClusterFacade core,
        final String bucket, final String username, final String password) {
        this.environment = environment;
        this.core = core;
        this.bucket = bucket;
        this.username = username;
        this.password = password;
    }

    public Observable<AsyncSearchQueryResult> execute(final SearchQuery query, final long timeout,
        final TimeUnit timeUnit) {
        return deferAndWatch(new Func1<Subscriber, Observable<SearchQueryResponse>>() {
            @Override
            public Observable<SearchQueryResponse> call(Subscriber subscriber) {
                final SearchQueryRequest request = new SearchQueryRequest(
                        query.indexName(),
                        query.export().toString(),
                        bucket,
                        username,
                        password
                );
                Utils.addRequestSpan(environment, request, "search");
                request.subscriber(subscriber);
                return applyTimeout(core.<SearchQueryResponse>send(request), request, environment, timeout, timeUnit);
            }
        })
        .flatMap(new Func1<SearchQueryResponse, Observable<SearchQueryResponse>>() {
            @Override
            public Observable<SearchQueryResponse> call(final SearchQueryResponse r) {
                if (shouldRetry(r.statusCode())) {
                    return Observable.error(new RetryableException(r));
                }
                return Observable.just(r);
            }
        })
        .retryWhen(RetryBuilder
            .anyOf(RetryableException.class)
            .max(10)
            .delay(Delay.exponential(TimeUnit.MILLISECONDS, 500, 2))
            .doOnRetry(new Action4<Integer, Throwable, Long, TimeUnit>() {
                @Override
                public void call(Integer attempt, Throwable error, Long delay, TimeUnit delayUnit) {
                    LOGGER.debug("Retrying {} because of {} (attempt {}, delay {} {})", query.export(),
                        error.getMessage(), attempt, delay, delayUnit);
            }
            })
            .build()
        )
        .map(new Func1<SearchQueryResponse, AsyncSearchQueryResult>() {
            @Override
            public AsyncSearchQueryResult call(final SearchQueryResponse response) {
                if (response.status().isSuccess()) {
                    JsonObject json = JsonObject.fromJson(response.payload());
                    return DefaultAsyncSearchQueryResult.fromJson(json);
                } else if (response.payload().contains("index not found")) {
                    return DefaultAsyncSearchQueryResult.fromIndexNotFound(query.indexName());
                } else if (response.status() == ResponseStatus.INVALID_ARGUMENTS) {
                    return DefaultAsyncSearchQueryResult.fromHttp400(response.payload());
                } else if (response.statusCode() == HTTP_PRECONDITION_FAILED) {
                    return DefaultAsyncSearchQueryResult.fromHttp412();
                } else {
                    throw new CouchbaseException("Could not query search index, "
                        + response.status() + ": " + response.payload());
                }
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends AsyncSearchQueryResult>>() {
            @Override
            public Observable<? extends AsyncSearchQueryResult> call(Throwable throwable) {
                if (throwable instanceof CannotRetryException) {
                    if (throwable.getCause() != null && throwable.getCause() instanceof RetryableException) {
                        RetryableException x = (RetryableException) throwable.getCause();
                        if (x.response().statusCode() == HTTP_TOO_MANY_REQUESTS) {
                            return Observable.just(DefaultAsyncSearchQueryResult.fromHttp429(x.response().payload()));
                        } else {
                            return Observable.error(new CouchbaseException("Could not query search index, "
                                + x.response().status() + ": " + x.response().payload()));
                        }
                    }
                }
                return Observable.error(throwable);
            }
        });
    }

    private static boolean shouldRetry(int statusCode) {
        return statusCode == HTTP_TOO_MANY_REQUESTS;
    }

    class RetryableException extends CouchbaseException {
        private final SearchQueryResponse response;
        RetryableException(final SearchQueryResponse response) {
            super("Retryable Error (" + response + ")");
            this.response = response;
        }

        public SearchQueryResponse response() {
            return response;
        }
    }
}
