        negotiate();
    }

    /**
     * Helper method to kick off the negotiation process.
     *
     * The first request against the server asks for a list of supported mechanisms.
     */
    private void negotiate() {
