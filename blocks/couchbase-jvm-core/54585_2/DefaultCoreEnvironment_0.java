
        if (builder.defaultMetricsLoggingConsumer != null) {
            metricsCollectorSubscription = eventBus
                .get()
                .filter(new Func1<CouchbaseEvent, Boolean>() {
                    @Override
                    public Boolean call(CouchbaseEvent evt) {
                        return evt.type().equals(EventType.METRIC);
                    }
                })
                .subscribe(builder.defaultMetricsLoggingConsumer);
        } else {
            metricsCollectorSubscription = null;
        }
