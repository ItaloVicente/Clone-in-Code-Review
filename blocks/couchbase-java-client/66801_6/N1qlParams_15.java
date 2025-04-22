        if (!this.credentials.isEmpty()) {
            JsonArray creds = JsonArray.create();
            for (Map.Entry<String, String> c : credentials.entrySet()) {
                creds.add(JsonObject.create()
                    .put("user", c.getKey())
                    .put("pass", c.getValue()));
            }
            queryJson.put("creds", creds);
        }

