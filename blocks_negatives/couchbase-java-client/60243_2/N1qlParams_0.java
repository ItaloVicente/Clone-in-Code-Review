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
