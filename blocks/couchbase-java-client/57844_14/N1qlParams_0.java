        if (this.mutationTokens != null) {
            if (this.consistency != null) {
                throw new IllegalArgumentException("`consistency(...)` cannot be used "
                    + "together with `consistentWith(...)`");
            }
            JsonObject vectors = JsonObject.create();
            for (Map.Entry<Bucket, List<MutationToken>> entry : mutationTokens.entrySet()) {
                JsonObject bucket = vectors.getObject(entry.getKey().name());

                if (bucket == null) {
                   bucket = JsonObject.create();
                   vectors.put(entry.getKey().name(), bucket);
                }

                for (MutationToken token : entry.getValue()) {
                    String vbid = String.valueOf(token.vbucketID());
                    if (!bucket.containsKey(vbid)
                        || bucket.getArray(vbid).getLong(0) < token.sequenceNumber()) {
                        bucket.put(vbid, JsonArray.from(
                            token.sequenceNumber(),
                            String.valueOf(token.vbucketUUID())
                        ));
                    }
                }
            }

            queryJson.put("scan_vectors", vectors);
            queryJson.put("scan_consistency", "at_plus");
        }
