                public Observable<GenericQueryResponse> call(final Subscriber subscriber) {
                    GetClusterConfigRequest request = new GetClusterConfigRequest();
                    request.subscriber(subscriber);
                    return core.<GetClusterConfigResponse>send(request)
                        .flatMap(new Func1<GetClusterConfigResponse, Observable<NodeInfo>>() {
                            @Override
                            public Observable<NodeInfo> call(GetClusterConfigResponse getClusterConfigResponse) {
                                return Observable.from(getClusterConfigResponse.config()
                                    .bucketConfig(bucket)
                                    .nodes());
                            }
                        }).filter(new Func1<NodeInfo, Boolean>() {
                            @Override
                            public Boolean call(NodeInfo nodeInfo) {
                                return nodeInfo.services().containsKey(ServiceType.QUERY)
                                    || nodeInfo.sslServices().containsKey(ServiceType.QUERY);
                            }
                        }).flatMap(new Func1<NodeInfo, Observable<GenericQueryResponse>>() {
                            @Override
                            public Observable<GenericQueryResponse> call(NodeInfo nodeInfo) {
                                try {
                                    InetAddress hostname = InetAddress.getByName(nodeInfo.hostname().address());
                                    GenericQueryRequest req = createN1qlRequest(query, bucket, username, password, hostname);
                                    req.subscriber(subscriber);
                                    return core.send(req);
                                } catch (UnknownHostException e) {
                                    return Observable.error(e);
                                }
                            }
                        });
