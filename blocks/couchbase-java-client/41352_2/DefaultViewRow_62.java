    public JsonDocument document(long timeout, TimeUnit timeUnit) {
        return asyncViewRow
            .document()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
