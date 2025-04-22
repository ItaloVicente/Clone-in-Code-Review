            })
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D append(D document) {
        return append(document, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D prepend(D document) {
        return prepend(document, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D append(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .append(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D prepend(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .prepend(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public Boolean close() {
        return close(environment.managementTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public Boolean close(long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .close()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
