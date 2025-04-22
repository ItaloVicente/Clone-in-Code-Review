        if (this.mutationTokens != null) {
            if (this.consistency != null) {
                throw new IllegalArgumentException("`consistency(...)` cannot be used "
                    + "together with `consistentWith(...)`");
            }
            JsonObject vectors = JsonObject.create();
            for (MutationToken token : this.mutationTokens) {
                JsonObject bucket = vectors.getObject(token.bucket());
                if (bucket == null) {
                    bucket = JsonObject.create();
                    vectors.put(token.bucket(), bucket);
                }
                bucket.put(
                    String.valueOf(token.vbucketID()),
                    JsonObject.create()
                        .put("guard", String.valueOf(token.vbucketUUID()))
                        .put("value", token.sequenceNumber())
                );
            }
            queryJson.put("scan_vector", vectors);
            queryJson.put("scan_consistency", "at_plus");
        }
