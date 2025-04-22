package com.couchbase.client.java.view;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.view.ViewQueryResponse;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.CouchbaseAsyncBucket;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

public class ViewRetryHandler {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(ViewRetryHandler.class);

    private static final ShouldRetryViewRequestException SHOULD_RETRY = new ShouldRetryViewRequestException();

    static {
        SHOULD_RETRY.setStackTrace(new StackTraceElement[]{});
    }

    public static Observable<ViewQueryResponse> retryOnCondition(final Observable<ViewQueryResponse> input) {
        return input
            .flatMap(new Func1<ViewQueryResponse, Observable<ViewQueryResponse>>() {
                @Override
                public Observable<ViewQueryResponse> call(final ViewQueryResponse response) {
                    return passThroughOrThrow(response);
                }
            })
            .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                @Override
                public Observable<?> call(Observable<? extends Throwable> observable) {
                    return observable
                        .flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof ShouldRetryViewRequestException) {
                                    return Observable.interval(10, TimeUnit.MILLISECONDS);
                                } else {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                }
            });
    }

    private static Observable<ViewQueryResponse> passThroughOrThrow(final ViewQueryResponse response) {
        final int responseCode = response.responseCode();
        if (responseCode == 200) {
            return Observable.just(response);
        }

        return response
            .info()
            .map(new Func1<ByteBuf, ViewQueryResponse>() {
                @Override
                public ViewQueryResponse call(ByteBuf byteBuf) {
                    ByteBuf infoCopy = byteBuf.copy();
                    JsonObject content = null;
                    try {
                        content =
                            CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.byteBufToJsonObject(infoCopy);
                    } catch (Exception e) {
                        throw new CouchbaseException("Could not parse View error message", e);
                    } finally {
                        infoCopy.release();
                    }

                    if (shouldRetry(responseCode, content)) {
                        throw SHOULD_RETRY;
                    }
                    return response;
                }
            })
            .singleOrDefault(response);
    }

    private static boolean shouldRetry(final int status, final JsonObject content) {
        switch (status) {
            case 200:
                return false;
            case 404:
                return analyse404Response(content);
            case 500:
                return analyse500Response(content);
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 401:
            case 408:
            case 409:
            case 412:
            case 416:
            case 417:
            case 501:
            case 502:
            case 503:
            case 504:
                return true;
            default:
                LOGGER.info("Received a View HTTP response code ({}) I did not expect, not retrying.", status);
                return false;
        }
    }

    private static boolean analyse404Response(final JsonObject content) {
        String stringified = content.toString();
        if (stringified.contains("not_found") && (stringified.contains("missing") || stringified.contains("deleted"))) {
            LOGGER.debug("Design document not found, error is {}", stringified);
            return false;
        }
        return true;
    }

    private static boolean analyse500Response(final JsonObject content) {
        String stringified = content.toString();
        if (stringified.contains("error") && stringified.contains("{not_found, missing_named_view}")) {
            LOGGER.debug("Design document not found, error is {}", stringified);
            return false;
        }
        return true;
    }

    private static class ShouldRetryViewRequestException extends CouchbaseException { }

}
