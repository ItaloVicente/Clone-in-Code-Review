        return deferAndWatch(new Func1<Subscriber, Observable<? extends Boolean>>() {
            @Override
            public Observable<? extends Boolean> call(final Subscriber subscriber) {
                return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<UpsertUserResponse>>() {
                        @Override
                        public Observable<UpsertUserResponse> call(Boolean aBoolean) {
                            UpsertUserRequest req = new UpsertUserRequest(username, password, domain.alias(), userid, payload);
                            req.subscriber(subscriber);
                            return core.send(req);
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
