                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    LOGGER.debug("Error while loading tainted config, ignoring", e);
                                }

                                @Override
                                public void onNext(GetBucketConfigResponse res) {
