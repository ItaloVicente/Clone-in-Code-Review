        if (builder.keyValueServiceConfig != null) {
            this.keyValueServiceConfig = builder.keyValueServiceConfig;
        } else {
            this.keyValueServiceConfig = KeyValueServiceConfig.create(kvEndpoints());
        }

        if (builder.viewServiceConfig != null) {
            this.viewServiceConfig = builder.viewServiceConfig;
        } else {
            this.viewServiceConfig = ViewServiceConfig.create(0, viewEndpoints());
        }

        if (builder.queryServiceConfig != null) {
            this.queryServiceConfig = builder.queryServiceConfig;
        } else {
            this.queryServiceConfig = QueryServiceConfig.create(0, queryEndpoints());
        }

        if (builder.searchServiceConfig != null) {
            this.searchServiceConfig = builder.searchServiceConfig;
        } else {
            this.searchServiceConfig = SearchServiceConfig.create(0, searchEndpoints());
        }

