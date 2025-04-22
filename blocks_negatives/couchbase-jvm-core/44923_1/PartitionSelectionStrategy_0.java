    /**
     * Helper method to select the first connected endpoint if no particular pinning is needed.
     *
     * @param endpoints the list of endpoints.
     * @return the first connected or null if none found.
     */
    private static final Endpoint selectFirstConnected(final Endpoint[] endpoints) {
        for (Endpoint endpoint : endpoints) {
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }
        return null;
