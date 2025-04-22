                            @Override
                            public Observable<GetResponse> call(Subscriber s) {
                                request.subscriber(s);
                                return core.send(request);
                            }
                        }).filter(new GetAndLockFilter(environment))
                                .map(new GetMap<D>(environment, transcoders, target, id)),
                        request, environment, timeout, timeUnit);
