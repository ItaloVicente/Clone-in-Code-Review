        refresher
            .registerBucket(bucketConfig.name(), bucketConfig.username(), bucketConfig.password())
            .subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {
                    LOGGER.trace("Config refresh stream for bucket {} ended.", bucketConfig.name());
                }

                @Override
                public void onError(Throwable e) {
                    LOGGER.warn("Error while registerting config for refresh", e);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                }
            });
