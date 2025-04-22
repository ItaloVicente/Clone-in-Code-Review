
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
            .timer(1, TimeUnit.MICROSECONDS, scheduler)
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long ignored) {
                    CouchbaseEvent event = generateCouchbaseEvent();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.trace("Manually Triggering Metric Emit to EventBus: {}", event);
                    }
                    eventBus.publish(event);
                }
            });
    }
