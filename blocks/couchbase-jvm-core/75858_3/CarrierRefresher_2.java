        LOGGER.debug("Shutting down the CarrierRefresher.");
        return Observable
            .just(true)
            .doOnNext(new Action1<Boolean>() {
                @Override
                public void call(Boolean ignored) {
                    if (pollerSubscription != null && !pollerSubscription.isUnsubscribed()) {
                        pollerSubscription.unsubscribe();
                    }
                }
            });
