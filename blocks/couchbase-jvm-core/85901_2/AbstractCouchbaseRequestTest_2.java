
package com.couchbase.client.core.message;

import org.junit.Test;
import rx.observers.TestSubscriber;

import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;

public class AbstractCouchbaseRequestTest {

    @Test
    public void shouldCompleteResponseWithSuccess() {
        SampleRequest sr = new SampleRequest();
        TestSubscriber<CouchbaseResponse> subs = new TestSubscriber<CouchbaseResponse>();
        sr.observable().subscribe(subs);
        subs.assertNotCompleted();

        CouchbaseResponse mockResponse = mock(CouchbaseResponse.class);
        sr.succeed(mockResponse);

        subs.awaitTerminalEvent();
        subs.assertCompleted();
        subs.assertValue(mockResponse);
    }

    @Test
    public void shouldCompleteResponseWithFailure() {
        SampleRequest sr = new SampleRequest();
        TestSubscriber<CouchbaseResponse> subs = new TestSubscriber<CouchbaseResponse>();
        sr.observable().subscribe(subs);
        subs.assertNotCompleted();

        Exception exception = new TimeoutException();
        sr.fail(exception);

        subs.awaitTerminalEvent();
        subs.assertError(exception);
    }

    class SampleRequest extends AbstractCouchbaseRequest {
        public SampleRequest() {
            super("bucket", "password");
        }
    }

}
