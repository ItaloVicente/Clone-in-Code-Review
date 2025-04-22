    public <D extends Document<?>> D document(Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncViewRow
            .document(target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .singleOrDefault(null);
