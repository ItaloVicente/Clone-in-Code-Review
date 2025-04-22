    @Override
    public Observable<Boolean> upsertUser(final String userid, final UserSettings userSettings) {
        final String payload = getUserSettingsPayload(userSettings);
        return ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<UpsertUserResponse>>() {
                            @Override
                            public Observable<UpsertUserResponse> call(Boolean aBoolean) {
                                return core.send(new UpsertUserRequest(userid, payload, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<UpsertUserResponse, Boolean>() {
                            @Override
                            public Boolean call(UpsertUserResponse response) {
                                if (!response.status().isSuccess()) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Could not update user: ");
                                    sb.append(response.status());
                                    if (response.message().length() > 0) {
                                        sb.append(", ");
                                        sb.append("msg: ");
                                        sb.append(response.message());
                                    }
                                    throw new CouchbaseException(sb.toString());
                                }
                                return true;
                            }
                        });
    }

    @Override
    public Observable<Boolean> removeUser(final String userid) {
        return ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<RemoveUserResponse>>() {
                            @Override
                            public Observable<RemoveUserResponse> call(Boolean aBoolean) {
                                return core.send(new RemoveUserRequest(userid, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveUserResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveUserResponse response) {
                                return response.status().isSuccess();
                            }
                        });
    }


