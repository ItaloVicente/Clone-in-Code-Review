        return cluster()
            .<BucketStreamingResponse>send(new BucketStreamingRequest(TERSE_PATH, name, password))
            .onErrorResumeNext(new Func1<Throwable, Observable<BucketStreamingResponse>>() {
                   @Override
                   public Observable<BucketStreamingResponse> call(Throwable throwable) {
                       return cluster().send(new BucketStreamingRequest(VERBOSE_PATH, name, password));
                   }
               }
            )
            .map(new Func1<BucketStreamingResponse, Boolean>() {
                @Override
                public Boolean call(final BucketStreamingResponse response) {
                    response
                        .configs()
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                return s.replace("$HOST", response.host());
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(final String rawConfig) {
                                pushConfig(rawConfig);
                            }
                        });
                    return true;
                }
            });
