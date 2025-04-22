
    @Override
    public D newDocument(String id, int expiry, T content, long cas, MutationToken mutationToken) {
        LOGGER.warn("This transcoder ({}) does not support mutation tokens - this method is a " +
            "stub and needs to be implemented on custom transcoders.", this.getClass().getSimpleName());
        return newDocument(id, expiry, content, cas);
    }
