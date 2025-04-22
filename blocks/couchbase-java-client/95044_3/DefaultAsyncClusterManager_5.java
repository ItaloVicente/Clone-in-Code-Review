        return deferAndWatch(new Func1<Subscriber, Observable<? extends ClusterInfo>>() {
            @Override
            public Observable<? extends ClusterInfo> call(final Subscriber subscriber) {
                return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<ClusterConfigResponse>>() {
                        @Override
                        public Observable<ClusterConfigResponse> call(Boolean aBoolean) {
                            ClusterConfigRequest req = new ClusterConfigRequest(username, password);
                            req.subscriber(subscriber);
                            return core.send(req);
