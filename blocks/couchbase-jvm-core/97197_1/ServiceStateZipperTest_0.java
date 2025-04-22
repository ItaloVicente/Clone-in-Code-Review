
package com.couchbase.client.core.node;

import com.couchbase.client.core.service.Service;
import com.couchbase.client.core.state.LifecycleState;
import org.junit.Test;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceStateZipperTest {

    @Test
    public void shouldHandleDegradedService() {
        ServiceStateZipper zipper = new ServiceStateZipper(LifecycleState.DISCONNECTED);

        assertEquals(LifecycleState.DISCONNECTED, zipper.state());

        Service configService = mock(Service.class);
        when(configService.states()).thenReturn(Observable.just(LifecycleState.IDLE));
        zipper.register(configService, configService);

        assertEquals(LifecycleState.IDLE, zipper.state());

        Service queryService = mock(Service.class);
        when(queryService.states()).thenReturn(Observable.just(LifecycleState.DEGRADED));
        zipper.register(queryService, queryService);

        assertEquals(LifecycleState.DEGRADED, zipper.state());
    }
}
