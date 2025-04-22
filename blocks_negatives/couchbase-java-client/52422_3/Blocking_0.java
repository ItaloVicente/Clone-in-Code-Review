        observable
            .subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    latch.countDown();
                }

                @Override
                public void onError(final Throwable e) {
                    returnException.set(e);
                    latch.countDown();
                }

                @Override
                public void onNext(final T item) {
                    returnItem.set(item);
                }
            });
