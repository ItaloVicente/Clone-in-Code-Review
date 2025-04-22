                if (c.getKey() != null && !c.getKey().isEmpty()) {
                    creds.add(JsonObject.create()
                        .put("user", c.getKey())
                        .put("pass", c.getValue()));
                }
            }
            if (!creds.isEmpty()) {
                queryJson.put("creds", creds);
