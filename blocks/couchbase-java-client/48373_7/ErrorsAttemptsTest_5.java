
package com.couchbase.client.java.util.retry;

import static org.junit.Assert.*;

import java.util.List;

import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.java.error.CannotRetryException;
import org.junit.Test;
import rx.Observable;

public class ErrorsAttemptsTest {

    @Test
    public void testErrorsWithAttempts() throws Exception {
        Observable<Throwable> errors = Observable.<Throwable>just(new CannotRetryException("")).repeat(10);
        Observable<Tuple2<Integer, Throwable>> errorsWithAttempts = Retry.errorsWithAttempts(
                errors, 10);

        List<Tuple2<Integer, Throwable>> list = errorsWithAttempts.toList().toBlocking().first();

        assertEquals(10, list.size());
        int expectedNumber = 1;
        for (Tuple2<Integer, Throwable> tuple2 : list) {
            assertEquals(expectedNumber++, tuple2.value1().intValue());
        }
    }
    @Test
    public void testErrorsWithAttemptsIsBoundedByErrors() throws Exception {
        Observable<Throwable> errors = Observable.<Throwable>just(new CannotRetryException("")).repeat(10);
        Observable<Tuple2<Integer, Throwable>> errorsWithAttempts = Retry.errorsWithAttempts(
                errors, 100);

        List<Tuple2<Integer, Throwable>> list = errorsWithAttempts.toList().toBlocking().first();

        assertEquals(10, list.size());
    }
    @Test
    public void testErrorsWithAttemptsIsBoundedByMaxAttempts() throws Exception {
        Observable<Throwable> errors = Observable.<Throwable>just(new CannotRetryException("")).repeat(100);
        Observable<Tuple2<Integer, Throwable>> errorsWithAttempts = Retry.errorsWithAttempts(
                errors, 10);

        List<Tuple2<Integer, Throwable>> list = errorsWithAttempts.toList().toBlocking().first();

        assertEquals(10, list.size());
    }
}
