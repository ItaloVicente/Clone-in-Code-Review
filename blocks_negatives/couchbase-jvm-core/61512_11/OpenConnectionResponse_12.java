    /**
     * Sets the required properties for the response.
     *
     * @param status  the status of the response.
     * @param request
     */
    public OpenConnectionResponse(final ResponseStatus status, final DCPConnection connection, final CouchbaseRequest request) {
        super(status, request);
