                            if (future.isSuccess()) {
                                subscriber.onNext(true);
                                shutdown = true;
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(future.cause());
