    private Map<ServiceType, Integer> parseSslServices(final Map<String, Integer> input) {
        Map<ServiceType, Integer> services = new HashMap<ServiceType, Integer>();
        for (Map.Entry<String, Integer> entry : input.entrySet()) {
            String type = entry.getKey();
            Integer port = entry.getValue();
            if (type.equals("sslDirect")) {
                services.put(ServiceType.BINARY, port);
            } else if (type.equals("httpsCAPI")) {
                services.put(ServiceType.VIEW, port);
            } else if (type.equals("httpsMgmt")) {
                services.put(ServiceType.CONFIG, port);
            }
        }
        return services;
    }

