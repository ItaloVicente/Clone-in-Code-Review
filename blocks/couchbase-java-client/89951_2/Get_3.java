package com.couchbase.client.java.bucket.api;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.ObserveRequest;
import com.couchbase.client.core.message.kv.ObserveResponse;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import io.opentracing.Scope;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.couchbase.client.java.bucket.api.Utils.applyTimeout;
import static com.couchbase.client.java.util.OnSubscribeDeferAndWatch.deferAndWatch;

@InterfaceAudience.Private
@InterfaceStability.Uncommitted
public class Exists {

  public static Observable<Boolean> exists(final String id, final CouchbaseEnvironment environment,
    final ClusterFacade core, final String bucket, final long timeout, final TimeUnit timeUnit) {
    return deferAndWatch(new Func1<Subscriber, Observable<Boolean>>() {
      @Override
      public Observable<Boolean> call(Subscriber s) {
        final ObserveRequest request = new ObserveRequest(id, 0, true, (short) 0, bucket);

        if (environment.tracingEnabled()) {
          Scope scope = environment.tracer()
            .buildSpan("exists")
            .startActive(false);
          request.span(scope.span(), environment);
          scope.close();
        }

        request.subscriber(s);
        Observable<Boolean> result = core.<ObserveResponse>send(request)
          .map(new Func1<ObserveResponse, Boolean>() {
          @Override
          public Boolean call(ObserveResponse response) {
            ByteBuf content = response.content();
            if (content != null && content.refCnt() > 0) {
              content.release();
            }

            if (environment.tracingEnabled()) {
              environment.tracer().scopeManager()
                .activate(response.request().span(), true)
                .close();
            }

            ObserveResponse.ObserveStatus foundStatus = response.observeStatus();
            return foundStatus == ObserveResponse.ObserveStatus.FOUND_PERSISTED
              || foundStatus == ObserveResponse.ObserveStatus.FOUND_NOT_PERSISTED;
          }
        });
        return applyTimeout(result, request, environment, timeout, timeUnit);
      }
    });
  }
}
