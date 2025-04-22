
    @Test
    public void testAuthenticatorWithMultipleCredentialsAddsCreds() {
        Authenticator auth = new PasswordAuthenticator()
                .bucket("foo", "a")
                .bucket("bar", "b");

        JsonObject expected1 = JsonObject.create().put("user", "foo").put("pass", "a");
        JsonObject expected2 = JsonObject.create().put("user", "bar").put("pass", "b");

        ClusterFacade core = mock(ClusterFacade.class);
        N1qlQuery query = N1qlQuery.simple("SELECT * FROM somewhere");
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(core,
                "main", "", auth,
                new LRUCache<String, PreparedPayload>(2), false));

        executor.execute(query);
        JsonObject n1ql = query.n1ql();

        assertTrue(n1ql.containsKey("creds"));
        assertThat(n1ql.getArray("creds")).containsOnly(expected1, expected2);
    }

    @Test
    public void testAuthenticatorWithSingleDifferentCredentialAddsCreds() {
        Authenticator auth = new PasswordAuthenticator()
                .bucket("foo", "a");

        JsonObject expected1 = JsonObject.create().put("user", "foo").put("pass", "a");

        ClusterFacade core = mock(ClusterFacade.class);
        N1qlQuery query = N1qlQuery.simple("SELECT * FROM somewhere");
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(core,
                "main", "", auth,
                new LRUCache<String, PreparedPayload>(2), false));

        executor.execute(query);
        JsonObject n1ql = query.n1ql();

        assertTrue(n1ql.containsKey("creds"));
        assertThat(n1ql.getArray("creds")).containsOnly(expected1);
    }

    @Test
    public void testAuthenticatorWithSingleCredentialSameAsBucketDoesntAddCreds() {
        Authenticator auth = new PasswordAuthenticator()
                .bucket("main", "a");

        ClusterFacade core = mock(ClusterFacade.class);
        N1qlQuery query = N1qlQuery.simple("SELECT * FROM somewhere");
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(core,
                "main", "", auth,
                new LRUCache<String, PreparedPayload>(2), false));

        executor.execute(query);
        JsonObject n1ql = query.n1ql();

        assertFalse(n1ql.containsKey("creds"));
    }
