                        return deferAndWatch(new Func1<Subscriber, Observable<? extends GetUsersResponse>>() {
                            @Override
                            public Observable<? extends GetUsersResponse> call(Subscriber subscriber) {
                                GetUsersRequest req;
                                if (userid == null || userid.isEmpty()) {
                                    req = GetUsersRequest.usersFromDomain(username, password, domain.alias());
                                } else {
                                    req = GetUsersRequest.user(username, password, domain.alias(), userid);
                                }
                                req.subscriber(subscriber);
                                return core.send(req);
                            }
                        });
