            JsonObject vectors = JsonObject.create();
            for (MutationToken token : mutationTokens) {
                JsonObject bucket = vectors.getObject(token.bucket());
                if (bucket == null) {
                    bucket = JsonObject.create();
                    vectors.put(token.bucket(), bucket);
                }

                bucket.put(String.valueOf(token.vbucketID()), JsonArray.from(
                    token.sequenceNumber(),
                    String.valueOf(token.vbucketUUID())
                ));
            }

            queryJson.put("scan_vectors", vectors);
