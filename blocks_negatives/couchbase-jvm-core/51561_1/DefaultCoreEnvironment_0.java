            public void call(final Subscriber<? super Boolean> subscriber) {
                if (shutdown) {
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                }

                ioPool.shutdownGracefully().addListener(new GenericFutureListener() {
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        if (!subscriber.isUnsubscribed()) {
                            if (future.isSuccess()) {
                                subscriber.onNext(future.isSuccess());
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(future.cause());
                            }
                        }
                    }
                });
