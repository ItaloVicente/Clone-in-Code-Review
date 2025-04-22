            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    LOGGER.debug("Explicitly closing bucket {} after failed open attempt to clean resources.", bucket);
                    removeBucketConfig(bucket);
                }
            })
