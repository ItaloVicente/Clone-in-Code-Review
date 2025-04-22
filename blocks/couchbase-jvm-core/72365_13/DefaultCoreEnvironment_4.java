        if (builder.keyValueServiceConfig != null) {
            this.keyValueServiceConfig = builder.keyValueServiceConfig;
        } else {
            this.keyValueServiceConfig = KeyValueServiceConfig.create(kvEndpoints());
        }

        if (builder.viewServiceConfig != null) {
            this.viewServiceConfig = builder.viewServiceConfig;
        } else {
            int minEndpoints = viewEndpoints() == VIEW_ENDPOINTS ? 0 : viewEndpoints();
            this.viewServiceConfig = ViewServiceConfig.create(minEndpoints, viewEndpoints());
        }

        if (builder.queryServiceConfig != null) {
            this.queryServiceConfig = builder.queryServiceConfig;
        } else {
            int minEndpoints = queryEndpoints() == VIEW_ENDPOINTS ? 0 : queryEndpoints();
            this.queryServiceConfig = QueryServiceConfig.create(minEndpoints, queryEndpoints());
        }

        if (builder.searchServiceConfig != null) {
            this.searchServiceConfig = builder.searchServiceConfig;
        } else {
            int minEndpoints = searchEndpoints() == VIEW_ENDPOINTS ? 0 : searchEndpoints();
            this.searchServiceConfig = SearchServiceConfig.create(minEndpoints, searchEndpoints());
        }

