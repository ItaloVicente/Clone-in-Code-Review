    /**
     * Sets current request.
     *
     * FIXME this is temporary solution for {@link com.couchbase.client.core.endpoint.dcp.DCPHandler}
     * @param request request to become the current one
     */
    protected void currentRequest(REQUEST request) {
        currentRequest = request;
    }

