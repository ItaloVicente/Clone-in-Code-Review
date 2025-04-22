        return
            ensureServiceEnabled()
                .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                    @Override
                    public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                        return core.send(new RemoveBucketRequest(name, username, password));
                    }
                })
                .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                .map(new Func1<RemoveBucketResponse, Boolean>() {
                @Override
                public Boolean call(RemoveBucketResponse response) {
                    return response.status().isSuccess();
                }
            });
