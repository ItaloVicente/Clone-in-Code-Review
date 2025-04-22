                    return deferAndWatch(new Func1<Subscriber, Observable<? extends ClusterConfigResponse>>() {
                        @Override
                        public Observable<? extends ClusterConfigResponse> call(Subscriber subscriber) {
                            ClusterConfigRequest request = new ClusterConfigRequest(username, password);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
