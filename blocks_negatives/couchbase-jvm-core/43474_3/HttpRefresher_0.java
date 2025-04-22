    @Override
    public Observable<Boolean> deregisterBucket(final String name) {
        LOGGER.debug("Deregistering bucket " + name + ".");
        return super.deregisterBucket(name);
    }

