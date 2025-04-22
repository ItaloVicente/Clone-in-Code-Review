
    public static boolean isDCPEnabled() throws Exception {
        ClusterConfigResponse response = cluster()
            .<ClusterConfigResponse>send(new ClusterConfigRequest("Administrator", "password"))
            .toBlocking()
            .single();
        return minNodeVersionFromConfig(response.config()) >= 3;
    }

    private static Integer minNodeVersionFromConfig(String rawConfig) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        Map<String, Object> result = mapper.readValue(rawConfig, type);

        List<Object> nodes = (List<Object>) result.get("nodes");
        int min = 99;
        for (Object n : nodes) {
            Map<String, Object> node = (Map<String, Object>) n;
            String version = (String) node.get("version");
            int major = Integer.parseInt(version.substring(0, 1));
            if (major < min) {
                min = major;
            }
        }
        return min;
    }
