        return subscriber.returnItem();
    }


    private static class TrackingSubscriber<T> extends Subscriber<T> {

        private final CountDownLatch latch;
        private volatile T returnItem = null;
        private volatile Throwable returnException = null;

        public TrackingSubscriber(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onCompleted() {
            latch.countDown();
        }

        @Override
        public void onError(final Throwable e) {
            returnException = e;
            latch.countDown();
        }

        @Override
        public void onNext(final T item) {
            returnItem = item;
        }

        public Throwable returnException() {
            return returnException;
        }

        public T returnItem() {
            return returnItem;
        }
