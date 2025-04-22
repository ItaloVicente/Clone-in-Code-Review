        return deferAndWatch(new Func1<Subscriber, Observable<? extends Boolean>>() {
            @Override
            public Observable<? extends Boolean> call(final Subscriber subscriber) {
                return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<RemoveUserResponse>>() {
                        @Override
                        public Observable<RemoveUserResponse> call(Boolean aBoolean) {
                            RemoveUserRequest req = new RemoveUserRequest(username, password, domain.alias(), userid);
                            req.subscriber(subscriber);
                            return core.send(req);
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
        });

