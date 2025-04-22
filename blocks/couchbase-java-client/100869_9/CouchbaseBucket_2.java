    @Override
    public byte[] exportAnalyticsDeferredResultHandle(AnalyticsDeferredResultHandle handle) {
        try {
            JsonObject jsonObject = JsonObject.create();
            jsonObject.put("v", 1);
            jsonObject.put("uri", handle.getStatusHandleUri());
            return JacksonTransformers.MAPPER.writeValueAsBytes(jsonObject);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot convert handle to Json String", e);
        }
    }

    @Override
    public AnalyticsDeferredResultHandle importAnalyticsDeferredResultHandle(byte[] b) {
        try {
            JsonObject jsonObj = CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(new String(b, StandardCharsets.UTF_8));
            if (jsonObj.getInt("v") != 1) {
                throw new IllegalArgumentException("Version is not supported");
            }
            return new DefaultAnalyticsDeferredResultHandle(new DefaultAsyncAnalyticsDeferredResultHandle(jsonObj.getString("uri"), this.environment(), this.core(), this.name(), username, password, environment.analyticsTimeout(), TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot import", e);
        }
    }

