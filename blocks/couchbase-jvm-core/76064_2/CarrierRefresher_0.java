        long pollInterval = environment.configPollInterval();
        if (pollInterval > 0) {
            LOGGER.debug("Starting polling with interval {}ms", pollInterval);
            pollerSubscription = Observable
                    .interval(pollInterval, TimeUnit.MILLISECONDS, environment.scheduler())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            provider().signalOutdated();
                        }
                    });
        } else {
            LOGGER.info("Proactive config polling disabled based on environment setting.");
        }
