    public Observable<Boolean> shutdown() {
        if (isShutdown()) {
            return Observable.just(true);
        }

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    for (PoolWorker worker : pool.eventLoops) {
                        if (!worker.isUnsubscribed()) {
                            worker.unsubscribe();
                        }
                    }
                    shutdown = true;
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }

