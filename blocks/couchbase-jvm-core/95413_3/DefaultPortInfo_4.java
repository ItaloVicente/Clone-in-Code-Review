        extractPorts(services, ports, sslPorts);
    }

    static void extractPorts(final Map<String, Integer> input, final Map<ServiceType, Integer> ports,
        final Map<ServiceType, Integer> sslPorts) {
        for (Map.Entry<String, Integer> entry : input.entrySet()) {
