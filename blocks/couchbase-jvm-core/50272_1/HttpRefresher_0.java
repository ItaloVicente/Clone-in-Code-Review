        });

        repeatConfigUntilUnsubscribed(name, response);

        return response.map(new Func1<BucketStreamingResponse, Boolean>() {
            @Override
            public Boolean call(BucketStreamingResponse response) {
                return response.status().isSuccess();
            }
        });
    }

    private void repeatConfigUntilUnsubscribed(final String name, Observable<BucketStreamingResponse> response) {
        response.flatMap(new Func1<BucketStreamingResponse, Observable<String>>() {
            @Override
            public Observable<String> call(final BucketStreamingResponse response) {
                LOGGER.debug("Config stream started for {} on {}.", name, response.host());

                return response
                    .configs()
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return s.replace("$HOST", response.host());
                        }
                    })
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            LOGGER.debug("Config stream ended for {} on {}.", name, response.host());
