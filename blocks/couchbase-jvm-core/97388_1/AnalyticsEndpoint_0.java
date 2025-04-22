            .addLast(new HttpClientCodec());

        boolean enableV2 = Boolean.parseBoolean(System.getProperty("com.couchbase.enableYasjlAnalyticsResponseParser", "true"));
        if (!enableV2) {
            pipeline.addLast(new AnalyticsHandler(this, responseBuffer(), false, false));
        } else {
            pipeline.addLast(new AnalyticsHandlerV2(this, responseBuffer(), false, false));
        }
