            .useKeys(JsonArray.from("key1", "key2"));
        assertEquals("FROM beer-sample USE KEYS [\"key1\",\"key2\"]", statement.toString());

        statement = new DefaultFromPath(null)
            .from("beer-sample")
            .useKeys("key1", "key2");
        assertEquals("FROM beer-sample USE KEYS [\"key1\",\"key2\"]", statement.toString());
