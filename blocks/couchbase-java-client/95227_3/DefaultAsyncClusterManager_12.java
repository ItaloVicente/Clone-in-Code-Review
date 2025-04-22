            .flatMap(new Func1<Boolean, Observable<RemoveUserResponse>>() {
                @Override
                public Observable<RemoveUserResponse> call(Boolean aBoolean) {
                    return deferAndWatch(new Func1<Subscriber, Observable<? extends RemoveUserResponse>>() {
                        @Override
                        public Observable<? extends RemoveUserResponse> call(Subscriber subscriber) {
                            RemoveUserRequest request = new RemoveUserRequest(
                                username, password, domain.alias(), userid);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
                }
            })
            .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
            .map(new Func1<RemoveUserResponse, Boolean>() {
                @Override
                public Boolean call(RemoveUserResponse response) {
                    return response.status().isSuccess();
                }
            });
