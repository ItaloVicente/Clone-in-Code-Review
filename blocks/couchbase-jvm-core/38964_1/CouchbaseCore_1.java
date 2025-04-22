                .flatMap(new Func1<ClusterConfig, Observable<ClusterConfig>>() {
                    @Override
                    public Observable<ClusterConfig> call(ClusterConfig clusterConfig) {
                        return requestHandler.reconfigure(clusterConfig);
                    }
                })
