    protected String getConfigureBucketPayload(BucketSettings settings, boolean includeName) {
        Map<String, Object> customSettings = settings.customSettings();
        Map<String, Object> actual = new LinkedHashMap<String, Object>(8 + customSettings.size());

        if (includeName) {
            actual.put("name", settings.name());
        }
        actual.put("ramQuotaMB", settings.quota());
        actual.put("authType", "sasl");
        actual.put("saslPassword", settings.password());
        actual.put("replicaNumber", settings.replicas());
        actual.put("proxyPort", settings.port());
        actual.put("bucketType", settings.type() == BucketType.COUCHBASE ? "membase" : "memcached");
        actual.put("flushEnabled", settings.enableFlush() ? "1" : "0");
        for (Map.Entry<String, Object> customSetting : customSettings.entrySet()) {
            if (actual.containsKey(customSetting.getKey()) || (!includeName && "name".equals(customSetting.getKey()))) {
                continue;
            }
            actual.put(customSetting.getKey(), customSetting.getValue());
        }

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> setting : actual.entrySet()) {
            sb.append('&').append(setting.getKey()).append('=').append(setting.getValue());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

