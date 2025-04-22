        try {
            this.rawHostname = trimPort(hostname);
            this.hostname = NetworkAddress.create(rawHostname);
        } catch (Exception e) {
            throw new CouchbaseException("Could not analyze hostname from config.", e);
        }
