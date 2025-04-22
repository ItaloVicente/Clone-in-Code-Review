
    public static <T> Observable<T> wrapColdWithAutoRelease(final Observable<T> source) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                source.subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onNext(T t) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(t);
                        } else {
                            ReferenceCountUtil.release(t);
                        }
                    }
                });
            }
        });
    }
