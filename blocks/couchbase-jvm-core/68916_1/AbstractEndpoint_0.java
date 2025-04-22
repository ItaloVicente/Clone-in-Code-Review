            public void call(final SingleSubscriber<? super ChannelFuture> ss) {
                bootstrap.connect().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture cf) throws Exception {
                        ss.onSuccess(cf);
                    }
                });
            }
        })
        .timeout(env.socketConnectTimeout() - 500, TimeUnit.MILLISECONDS)
        .onErrorResumeNext(new Func1<Throwable, Single<? extends ChannelFuture>>() {
            @Override
            public Single<? extends ChannelFuture> call(Throwable throwable) {
                ChannelPromise promise = new DefaultChannelPromise(null, env.ioPool().next());
                if (throwable instanceof TimeoutException) {
                    promise.setFailure(new ConnectTimeoutException("Connect callback did not return, "
                        + "hit safeguarding timeout."));
                } else {
                    promise.setFailure(throwable);
                }
                return Single.just(promise);
            }
        })
        .subscribe(new SingleSubscriber<ChannelFuture>() {
            @Override
            public void onSuccess(ChannelFuture future) {
