
    @Test
    public void toStringShouldContainJavaClientSpecificParameters() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();

        assertTrue(env.toString().contains("kvTimeout="));
    }
