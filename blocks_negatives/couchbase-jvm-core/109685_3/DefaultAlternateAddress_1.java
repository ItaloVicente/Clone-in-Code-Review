        try {
            this.rawHostname = hostname;
            this.hostname = NetworkAddress.create(rawHostname);
        } catch (Exception e) {
            throw new CouchbaseException("Could not analyze hostname from config.", e);
        }

        this.directServices = new HashMap<ServiceType, Integer>();
        this.sslServices = new HashMap<ServiceType, Integer>();
