    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);

        for (Map.Entry<NetworkLatencyMetricsIdentifier, LatencyMetric> metric : latencies().entrySet()) {
            NetworkLatencyMetricsIdentifier ident = metric.getKey();
            Map<String, Object> host = getOrCreate(ident.host(), result);
            Map<String, Object> service = getOrCreate(ident.service(), host);
            Map<String, Object> request = getOrCreate(ident.request(), service);
            Map<String, Object> status = getOrCreate(ident.status(), request);
            status.put("metrics", metric.getValue().export());
        }
        return result;
    }

    @SuppressWarnings({"unchecked"})
    private Map<String, Object> getOrCreate(String key, Map<String, Object> source) {
        Map<String, Object> found = (Map<String, Object>) source.get(key);
        if (found == null) {
            found = new HashMap<String, Object>();
            source.put(key, found);
        }
        return found;
    }

