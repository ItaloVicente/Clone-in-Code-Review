        @JsonProperty("couchApiBase") String viewUri,
        @JsonProperty("hostname") String hostname,
        @JsonProperty("ports") Map<String, Integer> ports) {
        if (hostname == null) {
            throw new CouchbaseException(new IllegalArgumentException("NodeInfo hostname cannot be null"));
        }

