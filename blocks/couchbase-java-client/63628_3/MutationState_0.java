
    public static MutationState from(String source) {
        return from(JsonObject.fromJson(source));
    }

    public static MutationState from(JsonObject source) {
        try {
            MutationState state = new MutationState();
            for (String bucketName : source.getNames()) {
                JsonObject bucket = source.getObject(bucketName);
                for (String vbid : bucket.getNames()) {
                    JsonArray values = bucket.getArray(vbid);
                    state.addToken(new MutationToken(
                        Long.parseLong(vbid),
                        Long.parseLong(values.getString(1)),
                        values.getLong(0),
                        bucketName
                    ));
                }
            }
            return state;
        } catch (Exception ex) {
            throw new IllegalStateException("Could not import MutationState from JSON.", ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutationState state = (MutationState) o;
        return tokens.containsAll(state.tokens) && state.tokens.containsAll(tokens);
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }

    @Override
    public String toString() {
        return "MutationState{tokens=" + tokens + '}';
    }
