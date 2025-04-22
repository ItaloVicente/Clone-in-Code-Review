        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LOGGER.warn(logIdent(ctx, endpoint) + "Got error while consuming KeepAliveResponse.", e);
        }

        @Override
        public void onNext(CouchbaseResponse couchbaseResponse) {
