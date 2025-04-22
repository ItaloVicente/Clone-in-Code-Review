
package com.couchbase.client.core.dcp;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.message.cluster.GetClusterConfigRequest;
import com.couchbase.client.core.message.cluster.GetClusterConfigResponse;
import com.couchbase.client.core.message.dcp.DCPRequest;
import com.couchbase.client.core.message.dcp.OpenConnectionRequest;
import com.couchbase.client.core.message.dcp.OpenConnectionResponse;
import com.couchbase.client.core.message.dcp.StreamRequestRequest;
import com.couchbase.client.core.message.dcp.StreamRequestResponse;
import rx.Observable;
import rx.functions.Func1;

public class BucketStreamAggregator {

    private final ClusterFacade core;
    private final String bucket;

    public BucketStreamAggregator(final ClusterFacade core, final String bucket) {
        this.core = core;
        this.bucket = bucket;
    }

    public Observable<DCPRequest> feed() {
        return core
            .<OpenConnectionResponse>send(new OpenConnectionRequest("dcpStream", bucket))
            .flatMap(new Func1<OpenConnectionResponse, Observable<Integer>>() {
                @Override
                public Observable<Integer> call(OpenConnectionResponse openConnectionResponse) {
                    return partitionSize();
                }
            })
            .flatMap(new Func1<Integer, Observable<DCPRequest>>() {
                @Override
                public Observable<DCPRequest> call(Integer numPartitions) {
                    return Observable.merge(
                        Observable
                            .range(0, numPartitions)
                            .flatMap(new Func1<Integer, Observable<StreamRequestResponse>>() {
                                @Override
                                public Observable<StreamRequestResponse> call(Integer partition) {
                                    return core.send(new StreamRequestRequest(partition.shortValue(), bucket));
                                }
                            })
                            .map(new Func1<StreamRequestResponse, Observable<DCPRequest>>() {
                                @Override
                                public Observable<DCPRequest> call(StreamRequestResponse response) {
                                    return response.stream();
                                }
                            })
                    );
                }
            });
    }

    private Observable<Integer> partitionSize() {
        return core
            .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
            .map(new Func1<GetClusterConfigResponse, Integer>() {
                @Override
                public Integer call(GetClusterConfigResponse response) {
                    CouchbaseBucketConfig config = (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                    return config.numberOfPartitions();
                }
            });
    }
}
