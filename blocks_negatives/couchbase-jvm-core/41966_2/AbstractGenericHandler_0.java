    /**
     * Sets the current request.
     *
     * Note that this method should normally not be used, only if a certain state needs to be replied even if a message
     * for it has already been transmitted (but more are expected).
     *
     * @param currentRequest the request to set.
     */
    protected void currentRequest(REQUEST currentRequest) {
        this.currentRequest = currentRequest;
    }

