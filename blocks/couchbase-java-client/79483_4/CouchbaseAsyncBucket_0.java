
    private static <X extends CouchbaseException, R extends CouchbaseResponse> X addDetails(X ex, R r) {
        if (r.statusDetails() != null) {
            ex.details(r.statusDetails());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{} returned with enhanced error details {}", r, ex);
            }
        }
        return ex;
    }
