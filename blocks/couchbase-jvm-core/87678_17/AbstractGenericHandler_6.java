    private static void completeResponse(final SlowOperationReporter reporter, final CouchbaseResponse response,
        final CouchbaseRequest request) {
        try {
            if (reporter != null && !request.isActive()) {
                reporter.reportZombie(response);
            }
        } catch (Exception ex) {
            LOGGER.info("Could not report zombie, ignoring. {}", ex);
        }
