    public JsonObject info(long timeout, TimeUnit timeUnit) {
        return asyncQueryResult
            .info()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
