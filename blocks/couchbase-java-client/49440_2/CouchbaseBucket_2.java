    @Override
    public boolean exists(String id) {
        return exists(id, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean exists(String id, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.exists(id), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> boolean exists(D document) {
        return exists(document.id());
    }

    @Override
    public <D extends Document<?>> boolean exists(D document, long timeout, TimeUnit timeUnit) {
        return exists(document.id(), timeout, timeUnit);
    }

