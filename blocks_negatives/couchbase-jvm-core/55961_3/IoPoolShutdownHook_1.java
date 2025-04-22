        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                ioPool.shutdownGracefully().addListener(new GenericFutureListener() {
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        if (!subscriber.isUnsubscribed()) {
                            try {
                                if (future.isSuccess()) {
                                    subscriber.onNext(true);
                                    shutdown = true;
                                    subscriber.onCompleted();
                                } else {
                                    subscriber.onError(future.cause());
                                }
                            } catch (Exception ex) {
                                subscriber.onError(ex);
                            }
                        }
                    }
                });
            }
        }).onErrorResumeNext(Observable.just(true));
