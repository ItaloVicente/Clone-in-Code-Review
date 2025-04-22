
package com.couchbase.client.core.message.stat;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.message.cluster.GetClusterConfigRequest;
import com.couchbase.client.core.message.cluster.GetClusterConfigResponse;
import com.couchbase.client.core.message.kv.StatRequest;
import com.couchbase.client.core.message.kv.StatResponse;
import com.couchbase.client.core.service.ServiceType;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Stat {
    public static Observable<StatResponse> call(final ClusterFacade core, final String bucket, final String key) {
        return sendStatRequests(core, bucket, key);
    }

    private static Observable<StatResponse> sendStatRequests(final ClusterFacade core, final String bucket, final String key) {
        return Observable.defer(new Func0<Observable<StatResponse>>() {
            @Override
            public Observable<StatResponse> call() {
                return core
                        .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                        .map(new Func1<GetClusterConfigResponse, List<InetAddress>>() {
                            @Override
                            public List<InetAddress> call(GetClusterConfigResponse response) {
                                CouchbaseBucketConfig conf =
                                        (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                                List<InetAddress> hostnames = new ArrayList<InetAddress>();
                                for (NodeInfo nodeInfo : conf.nodes()) {
                                    if (nodeInfo.services().containsKey(ServiceType.BINARY)) {
                                        hostnames.add(nodeInfo.hostname());
                                    }
                                }
                                return hostnames;
                            }
                        })
                        .flatMap(new Func1<List<InetAddress>, Observable<StatResponse>>() {
                            @Override
                            public Observable<StatResponse> call(List<InetAddress> hostnames) {
                                List<Observable<StatResponse>> stats = new ArrayList<Observable<StatResponse>>();
                                for (InetAddress hostname : hostnames) {
                                    stats.add(core.<StatResponse>send(new StatRequest(key, hostname, bucket)));
                                }
                                if (stats.size() == 1) {
                                    return stats.get(0);
                                } else {
                                    return Observable.mergeDelayError(Observable.from(stats));
                                }
                            }
                        });
            }
        });
    }
}
