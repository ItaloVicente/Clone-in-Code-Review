    @Override
    public KeyValueServiceConfig kvServiceConfig() {
        return KeyValueServiceConfig.create(kvEndpoints()); // TODO: fixme
    }

    @Override
    public QueryServiceConfig queryServiceConfig() {
        return QueryServiceConfig.create(0, 24); // TODO: fixme
    }

    @Override
    public ViewServiceConfig viewServiceConfig() {
        return ViewServiceConfig.create(0, 24); // TODO: fixme
    }

    @Override
    public SearchServiceConfig searchServiceConfig() {
        return SearchServiceConfig.create(0, 24); // TODO: fixme
    }

