    @Override
    @InterfaceStability.Experimental
    public Observable<AsyncClusterApiClient> apiClient() {
        return ensureServiceEnabled()
                .map(new Func1<Boolean, AsyncClusterApiClient>() {
                    @Override
                    public AsyncClusterApiClient call(Boolean aBoolean) {
                        return new AsyncClusterApiClient(username, password, core);
                    }
                });
    }

