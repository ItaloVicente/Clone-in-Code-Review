
        if (this.rawParams != null) {
            for (Map.Entry<String, Object> entry : rawParams.entrySet()) {
                queryJson.put(entry.getKey(), entry.getValue());
            }
        }
