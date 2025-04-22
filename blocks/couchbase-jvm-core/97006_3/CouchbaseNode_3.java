        if (alternate != null) {
            LOGGER.info("Disconnected from Node {} ({})",
                system(hostname.nameAndAddress()), system(alternate.nameAndAddress()));
        } else {
            LOGGER.info("Disconnected from Node {}", system(hostname.nameAndAddress()));
        }
