                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .doOnNext(new Action1<BucketsConfigResponse>() {
                        @Override
                        public void call(BucketsConfigResponse response) {
                            if (!response.status().isSuccess()) {
                                if (response.config().contains("Unauthorized")) {
                                    throw new InvalidPasswordException();
                                } else {
                                    throw new CouchbaseException(response.status() + ": " + response.config());
                                }
