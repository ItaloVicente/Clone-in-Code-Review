    @Override
    public int configServiceEndpoints() {
        int endpoints = getInt("service.endpoints.config");
        if (endpoints <= 0) {
            throw new EnvironmentException("At least one Endpoint per Service is required");
        }
        return endpoints;
    }

    @Override
    public int streamServiceEndpoints() {
        int endpoints = getInt("service.endpoints.stream");
        if (endpoints <= 0) {
            throw new EnvironmentException("At least one Endpoint per Service is required");
        }
        return endpoints;
    }

