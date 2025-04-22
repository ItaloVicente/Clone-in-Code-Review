package com.couchbase.client.java.util;

import org.junit.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BlockingTest {

    @Test
    public void blockForSingleShouldSucceed() {
        String result = Blocking.blockForSingle(Observable.just("Hello World"), 1, TimeUnit.SECONDS);
        assertEquals("Hello World", result);
    }

    @Test
    public void blockForSingleShouldAllowNull() {
        String result = Blocking.blockForSingle(Observable.<String>empty().singleOrDefault(null), 1, TimeUnit.SECONDS);
        assertNull(result);
    }

    @Test
    public void blockForSingleShouldTimeout() {
        try {
            Blocking.blockForSingle(Observable.timer(500, TimeUnit.MILLISECONDS).first(), 100, TimeUnit.MILLISECONDS);
            assertTrue(false);
        } catch (RuntimeException ex) {
            assertTrue(ex.getCause() instanceof TimeoutException);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void blockForSingleShouldRaiseError() {
        Blocking.blockForSingle(Observable.<String>error(new IllegalArgumentException()), 1, TimeUnit.SECONDS);
    }

}
