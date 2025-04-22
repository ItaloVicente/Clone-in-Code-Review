        if (r.statusDetails() != null) {
            ex.details(r.statusDetails());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{} returned with enhanced error details {}", r, ex);
            }
        }
        return ex;
