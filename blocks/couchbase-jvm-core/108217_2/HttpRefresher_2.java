                        });
                }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<BucketStreamingResponse>>() {
                @Override
                public Observable<BucketStreamingResponse> call(Throwable throwable) {
                    if (throwable instanceof TerseConfigDoesNotExistException) {
                        return cluster()
                            .<BucketStreamingResponse>send(new BucketStreamingRequest(VERBOSE_PATH, name, username, password))
                            .doOnNext(new Action1<BucketStreamingResponse>() {
                                @Override
                                public void call(BucketStreamingResponse response) {
                                    if (!response.status().isSuccess()) {
                                        throw new ConfigurationException("Could not load terse config.");
                                    }
                                }
                            });
                    } else {
                        return Observable.error(throwable);
                    }
                }
            });
