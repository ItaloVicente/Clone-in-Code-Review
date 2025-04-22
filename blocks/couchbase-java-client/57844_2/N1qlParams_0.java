        if (this.consistency == ScanConsistency.AT_PLUS) {
            if (this.mutationTokens == null || this.mutationTokens.length == 0) {
                throw new IllegalArgumentException("If ScanConsistency.AT_PLUS is used, at least "
                    + "one scanToken needs to be specified.");
            }
            JsonObject vectors = JsonObject.create();
            for (MutationToken token : this.mutationTokens) {
                vectors.put(
                    String.valueOf(token.vbucketID()),
                    JsonObject.create()
                        .put("guard", String.valueOf(token.vbucketUUID()))
                        .put("value", token.sequenceNumber())
                );
            }
            queryJson.put("scan_vector", vectors);
        }
