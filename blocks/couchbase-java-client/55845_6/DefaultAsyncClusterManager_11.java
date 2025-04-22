                })
                .doOnNext(new Action1<BucketsConfigResponse>() {
                    @Override
                    public void call(BucketsConfigResponse response) {
                        if (!response.status().isSuccess()) {
                            if (response.config().contains("Unauthorized")) {
                                throw new InvalidPasswordException();
