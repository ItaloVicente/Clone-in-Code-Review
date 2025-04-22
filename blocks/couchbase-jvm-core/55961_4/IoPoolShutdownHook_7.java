                ioPool.shutdownGracefully(0, 10, TimeUnit.MILLISECONDS).addListener(
                        new GenericFutureListener<Future<? super Object>>() {

                            @Override
                            public void operationComplete(Future<? super Object> future) throws Exception {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (!future.isSuccess()) {
                                    subscriber.onError(future.cause());
                                } else {
