    private Map<ServiceType, Integer> parseServices(final Map<String, Integer> input) {
        Map<ServiceType, Integer> services = new HashMap<ServiceType, Integer>();
        for (Map.Entry<String, Integer> entry : input.entrySet()) {
            String type = entry.getKey();
            Integer port = entry.getValue();
            if (type.equals("direct")) {
                services.put(ServiceType.BINARY, port);
            }
        }
        services.put(ServiceType.CONFIG, configPort);
        services.put(ServiceType.VIEW, URI.create(viewUri).getPort());
        return services;
    }

    private String trimPort(String hostname) {
