        if (builder.analyticsServiceConfig != null) {
            this.analyticsServiceConfig = builder.analyticsServiceConfig;
        } else {
            this.analyticsServiceConfig = AnalyticsServiceConfig.create(0, ANALYTICS_ENDPOINTS);
        }

