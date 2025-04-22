    @Override
    public Observable<Boolean> upsertUser(final String username, final UserSettings userSettings) {
        final String payload = getUserSettingsPayload(userSettings);
        return
                ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                            @Override
                            public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                                return core.send(new UpsertUserRequest(username, payload, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveBucketResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveBucketResponse response) {
                                return response.status().isSuccess();
                            }
                        });
    }

    @Override
    public Observable<Boolean> removeUser(final String username) {
        return
                ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                            @Override
                            public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                                return core.send(new RemoveUserRequest(username, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveBucketResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveBucketResponse response) {
                                return response.status().isSuccess();
                            }
                        });
    }


