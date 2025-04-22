    @Override
    public Observable<ClusterApiClient> apiClient() {
        return ensureServiceEnabled()
                .map(new Func1<Boolean, ClusterApiClient>() {
                    @Override
                    public ClusterApiClient call(Boolean aBoolean) {
                        return new ClusterApiClient(username, password, core, environment);
                    }
                });
    }

