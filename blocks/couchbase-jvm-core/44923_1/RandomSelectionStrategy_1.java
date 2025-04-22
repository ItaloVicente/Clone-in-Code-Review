    public Endpoint selectOne(final CouchbaseRequest request, final Endpoint[] endpoints) {
        List<Endpoint> selected = selectAllConnected(endpoints);
        if (selected.size() > 0) {
            Collections.shuffle(selected, RANDOM);
            return selected.get(0);
        } else {
