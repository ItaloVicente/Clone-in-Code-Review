        if (settings.port() > 0) {
            actual.put("proxyPort", settings.port());
        }

        String bucketType;
        switch(settings.type()) {
            case COUCHBASE: bucketType = "membase"; break;
            case MEMCACHED: bucketType = "memcached"; break;
            case EPHEMERAL: bucketType = "ephemeral"; break;
            default:
                throw new UnsupportedOperationException("Could not convert bucket type " + settings.type());
        }
        actual.put("bucketType", bucketType);
