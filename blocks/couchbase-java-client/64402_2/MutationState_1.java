    public JsonObject exportForFts() {
        JsonObject result = JsonObject.create();
        for (MutationToken token : tokens) {
            String tokenKey = token.vbucketID() + "/" + token.vbucketUUID();
            Long seqno = result.getLong(tokenKey);
            if (seqno == null || seqno < token.sequenceNumber()) {
                result.put(tokenKey, token.sequenceNumber());
            }
        }
        return result;
    }

