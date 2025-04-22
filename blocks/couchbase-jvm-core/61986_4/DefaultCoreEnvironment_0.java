
        try {
            System.setProperty("com.couchbase.client.deps.io.netty.packagePrefix", "com.couchbase.client.deps.");
        } catch (Exception ex) {
            LOGGER.warn("Could not configure bundled netty's package prefix", ex);
        }
