    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);
        result.put("openBuckets", openBuckets());

        Set<String> clusterNodes = new HashSet<String>();
        for (InetAddress node : clusterNodes()) {
            clusterNodes.add(node.toString());
        }
        result.put("clusterNodes", clusterNodes);

        return result;
    }

