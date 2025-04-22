                        .flatMap(new Func1<Boolean, Observable<RemoveUserResponse>>() {
                            @Override
                            public Observable<RemoveUserResponse> call(Boolean aBoolean) {
                                return core.send(new RemoveUserRequest(username, password, domain.alias(), userid));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveUserResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveUserResponse response) {
                                return response.status().isSuccess();
                            }
                        });
