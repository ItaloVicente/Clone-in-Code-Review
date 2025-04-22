                        final GetUsersRequest request = (userid == null || userid.isEmpty())
                            ? GetUsersRequest.usersFromDomain(username, password, domain.alias())
                            : GetUsersRequest.user(username, password, domain.alias(), userid);
                        return deferAndWatch(new Func1<Subscriber, Observable<? extends GetUsersResponse>>() {
                            @Override
                            public Observable<? extends GetUsersResponse> call(Subscriber subscriber) {
                                request.subscriber(subscriber);
                                return core.send(request);
                            }
                        });
