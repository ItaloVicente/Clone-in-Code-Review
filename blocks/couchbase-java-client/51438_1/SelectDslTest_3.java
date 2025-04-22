    @Test
    public void testJoinWithEscapedNamespace() {
        Statement statement = new DefaultFromPath(null).from("a")
                                   .join(i("beer-sample")).as("b")
                                   .onKeys(path("a", "foreignKey"));

        assertEquals("FROM a JOIN `beer-sample` AS b ON KEYS a.foreignKey", statement.toString());
    }

