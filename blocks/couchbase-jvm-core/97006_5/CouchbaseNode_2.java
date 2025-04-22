        if (alternate != null) {
            LOGGER.info("Connected to Node {} ({})",
                system(hostname.nameAndAddress()), system(alternate.nameAndAddress()));
        } else {
            LOGGER.info("Connected to Node {}", system(hostname.nameAndAddress()));
        }
