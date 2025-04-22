        return error(timeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonObject error(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncViewResult.error(), timeout, TIMEOUT_UNIT);
