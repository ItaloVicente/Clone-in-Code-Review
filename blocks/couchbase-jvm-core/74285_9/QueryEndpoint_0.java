
        pipeline.addLast(new HttpClientCodec());
        boolean enableV2 = "true".equals(System.getProperty("com.couchbase.enableYasjlQueryResponseParser", "false"));
        if (!enableV2) {
            pipeline.addLast(new QueryHandler(this, responseBuffer(), false, false));
        } else {
            pipeline.addLast(new QueryHandlerV2(this, responseBuffer(), false, false));
        }
