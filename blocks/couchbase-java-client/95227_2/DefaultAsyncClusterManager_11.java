            .flatMap(new Func1<Boolean, Observable<UpsertUserResponse>>() {
                @Override
                public Observable<UpsertUserResponse> call(Boolean aBoolean) {
                    return deferAndWatch(new Func1<Subscriber, Observable<? extends UpsertUserResponse>>() {
                        @Override
                        public Observable<? extends UpsertUserResponse> call(Subscriber subscriber) {
                            UpsertUserRequest request = new UpsertUserRequest(
                                username, password, domain.alias(), userid, payload);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
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
