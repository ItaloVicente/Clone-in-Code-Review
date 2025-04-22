
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void triggerEmit() {
        if (!isEnabled()) {
            return;
        }

        Observable
            .just(generateCouchbaseEvent())
            .subscribeOn(scheduler)
            .subscribe(new Action1<CouchbaseEvent>() {
                @Override
                public void call(CouchbaseEvent event) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.trace("Manually Triggering Metric Emit to EventBus: {}", event);
                    }
                    eventBus.publish(event);
                }
            });
    }
