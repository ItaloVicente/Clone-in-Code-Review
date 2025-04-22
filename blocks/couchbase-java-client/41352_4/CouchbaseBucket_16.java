    public JsonDocument getAndTouch(String id, int expiry, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndTouch(id, expiry)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .singleOrDefault(null);
