    @Override
    public int queryServiceEndpoints() {
        int endpoints = getInt("service.endpoints.query");
        if (endpoints <= 0) {
            throw new EnvironmentException("At least one Endpoint per Service is required");
        }
        return endpoints;
    }

