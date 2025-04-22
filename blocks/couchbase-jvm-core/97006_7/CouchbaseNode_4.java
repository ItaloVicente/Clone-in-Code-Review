
        String hostname = alternate != null
            ? convertedAlternate
            : convertedHostname;
        if (alternate != null) {
            LOGGER.debug(logIdent()
                + "Service {} is mapped to alternate hostname {}", request.type(), hostname);
        }
