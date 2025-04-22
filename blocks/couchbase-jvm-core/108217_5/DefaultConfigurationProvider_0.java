
        refresher
            .registerBucket(config.name(), config.username(), config.password())
            .subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {
                    LOGGER.trace("Config refresh stream for bucket {} ended.", config.name());
                }

                @Override
                public void onError(Throwable e) {
                    LOGGER.warn("Error while registering config for refresh", e);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                }
            });
