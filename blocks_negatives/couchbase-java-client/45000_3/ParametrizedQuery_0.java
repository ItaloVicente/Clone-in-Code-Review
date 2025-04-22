    public String toN1QL() {
        JsonObject query = JsonObject.create()
                .put(statementType(), statement.toString());
        if (params instanceof JsonArray && !((JsonArray) params).isEmpty()) {
           query.put("args", (JsonArray) params);
        } else if (params instanceof JsonObject && !((JsonObject) params).isEmpty()) {
            JsonObject namedParams = (JsonObject) params;
            for (String key : namedParams.getNames()) {
                Object value = namedParams.get(key);
                if (key.charAt(0) != '$') {
                    query.put('$' + key, value);
                } else {
                    query.put(key, value);
                }
            }
