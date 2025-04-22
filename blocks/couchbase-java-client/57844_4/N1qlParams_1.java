        if (this.mutatedDocuments != null) {
            if (this.consistency != null) {
                throw new IllegalArgumentException("`consistency(...)` cannot be used "
                    + "together with `consistentWith(...)`");
            }
            JsonObject vectors = JsonObject.create();
            for (Map.Entry<Bucket, List<MutatedDocument>> entry : mutatedDocuments.entrySet()) {
                JsonObject bucket = vectors.getObject(entry.getKey().name());

                if (bucket == null) {
                   bucket = JsonObject.create();
                   vectors.put(entry.getKey().name(), bucket);
                }

                if (entry.getValue().isEmpty()) {
                    throw new UnsupportedOperationException("Not supported (yet).");
                }

                for (MutatedDocument d : entry.getValue()) {
                    MutationToken token = d.mutationToken();
                    if (token == null) {
                       throw new UnsupportedOperationException("Not supported (yet).");
                    }

                    bucket.put(
                        String.valueOf(token.vbucketID()),
                        JsonObject.create()
                            .put("guard", String.valueOf(token.vbucketUUID()))
                            .put("value", token.sequenceNumber())
                    );
                }
            }

            queryJson.put("scan_vectors", vectors);
            queryJson.put("scan_consistency", "at_plus");
        }
