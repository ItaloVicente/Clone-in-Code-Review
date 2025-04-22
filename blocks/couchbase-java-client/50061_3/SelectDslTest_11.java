            .useKeys("a.id");
        assertEquals("FROM beer-sample AS b USE KEYS a.id", statement.toString());

        statement = new DefaultFromPath(null)
            .from("beer-sample")
            .as("b")
            .useKeysValues("my-brewery");
