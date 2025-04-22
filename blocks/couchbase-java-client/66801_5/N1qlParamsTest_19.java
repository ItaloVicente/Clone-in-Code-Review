
    @Test
    public void shouldInjectCredentialsFromPairs() {
        N1qlParams source = N1qlParams.build()
                .withCredentials("foo", "a")
                .withCredentials("bar", "b");

        JsonObject expected = JsonObject.create().put("creds", JsonArray.from(
                JsonObject.create().put("user", "bar").put("pass", "b"),
                JsonObject.create().put("user", "foo").put("pass", "a")
        ));
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectCredentialsFromCredentialList() {
        List<Credential> credentialList = new ArrayList<Credential>(2);
        credentialList.add(new Credential("foo", "a"));
        credentialList.add(new Credential("bar", "b"));

        N1qlParams source = N1qlParams.build()
                .withCredentials(credentialList);

        JsonObject expected = JsonObject.create().put("creds", JsonArray.from(
                JsonObject.create().put("user", "bar").put("pass", "b"),
                JsonObject.create().put("user", "foo").put("pass", "a")
        ));
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReplaceSameLoginCredentials() {
        List<Credential> credentialList = new ArrayList<Credential>(2);
        credentialList.add(new Credential("foo", "a"));
        credentialList.add(new Credential("bar", "b"));

        N1qlParams source = N1qlParams.build()
                .withCredentials(credentialList)
                .withCredentials("bar", "c");

        JsonObject expected = JsonObject.create().put("creds", JsonArray.from(
                JsonObject.create().put("user", "bar").put("pass", "c"),
                JsonObject.create().put("user", "foo").put("pass", "a")
        ));
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }
