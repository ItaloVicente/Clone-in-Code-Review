    }
    private Bucket parseBucketFromJSON(JSONObject bucketJO) throws ParseException {
        try {
            String bucketname = bucketJO.get("name").toString();
            String streamingUri = bucketJO.get("streamingUri").toString();
            ConfigFactory cf = new DefaultConfigFactory();
            Config config = cf.create(bucketJO);
            List<Node> nodes = new ArrayList<Node>();
            JSONArray nodesJA = bucketJO.getJSONArray("nodes");
            for(int i = 0; i < nodesJA.length(); ++i) {
                JSONObject nodeJO = nodesJA.getJSONObject(i);
                String statusValue = nodeJO.get("status").toString();
                Status status = null;
                try {
                    status = Status.valueOf(statusValue);
                } catch (IllegalArgumentException e) {
                    getLogger().error("Unknown status value: " + statusValue);
                }
                String hostname = nodeJO.get("hostname").toString();
                JSONObject portsJO = nodeJO.getJSONObject("ports");
                Map<Port, String> ports = new HashMap<Port, String>();
                for (Port port : Port.values()) {
                    String portValue = portsJO.get(port.toString()).toString();
                    if (portValue == null || portValue.isEmpty()) {
                        continue;
                    }
                    ports.put(port, portValue);
                }
                Node node = new Node(status, hostname, ports);
                nodes.add(node);
            }
            return new Bucket(bucketname, config, new URI(streamingUri), nodes);
        } catch (JSONException e) {
            throw new ParseException(e.getMessage(), 0);
        } catch (URISyntaxException e) {
            throw new ParseException(e.getMessage(), 0);
