    @Test
    public void shouldInjectLocalCredentialForBucket() {
        QueryParams p = QueryParams.build()
                                   .addCredential("bucket", "pwd");

        JsonObject expectedCred = JsonObject.create()
                .put("user", "local:bucket")
                .put("pass", "pwd");

        JsonObject actual = JsonObject.empty();
        p.injectParams(actual);

        assertTrue(actual.containsKey("creds"));
        assertFalse(actual.getArray("creds").isEmpty());
        assertEquals(expectedCred, actual.getArray("creds").get(0));
    }

    @Test
    public void shouldInjectAdminCredentialForAdmin() {
        QueryParams p = QueryParams.build()
                                   .addAdminCredential("john", "pwd");

        JsonObject expectedCred = JsonObject.create()
                                            .put("user", "admin:john")
                                            .put("pass", "pwd");

        JsonObject actual = JsonObject.empty();
        p.injectParams(actual);

        assertTrue(actual.containsKey("creds"));
        assertFalse(actual.getArray("creds").isEmpty());
        assertEquals(expectedCred, actual.getArray("creds").get(0));
    }

    @Test
    public void shouldAppendCredentials() {
        QueryParams p = QueryParams.build()
                .addCredential("bucket", "pwd")
                .addAdminCredential("john", "pwd")
                .addCredential("bucket2", "pwd2");

        JsonObject actual = JsonObject.empty();
        p.injectParams(actual);

        assertNotNull(actual.getArray("creds"));
        assertEquals(3, actual.getArray("creds").size());
    }

