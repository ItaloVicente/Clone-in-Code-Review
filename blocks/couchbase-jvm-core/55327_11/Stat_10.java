
package com.couchbase.client.core.message.stat;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.message.internal.GetNodesRequest;
import com.couchbase.client.core.message.internal.GetNodesResponse;
import com.couchbase.client.core.message.kv.StatRequest;
import com.couchbase.client.core.message.kv.StatResponse;
import com.couchbase.client.core.service.ServiceType;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

public class Stat {
    public static Observable<StatResponse> call(final ClusterFacade core, final String bucket, final String key) {
        return sendStatRequests(core, bucket, key);
    }

    private static Observable<StatResponse> sendStatRequests(final ClusterFacade core, final String bucket, final String key) {
        return Observable.defer(new Func0<Observable<StatResponse>>() {
            @Override
            public Observable<StatResponse> call() {
                Observable<Observable<StatResponse>> stats = core
                        .<GetNodesResponse>send(new GetNodesRequest(ServiceType.BINARY, bucket))
                        .map(new Func1<GetNodesResponse, Observable<StatResponse>>() {
                            @Override
                            public Observable<StatResponse> call(GetNodesResponse node) {
                                return core.send(new StatRequest(key, node.hostname(), bucket));
                            }
                        });
                return Observable.mergeDelayError(stats);
            }
        });
    }
}
