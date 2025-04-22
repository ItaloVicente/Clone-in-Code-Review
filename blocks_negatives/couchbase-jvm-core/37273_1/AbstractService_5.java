    /**
     * Create a new {@link Endpoint} to be used by this service.
     *
     * @return the endpoint to be used.
     */
    protected abstract Endpoint newEndpoint(final RingBuffer<ResponseEvent> responseBuffer);

