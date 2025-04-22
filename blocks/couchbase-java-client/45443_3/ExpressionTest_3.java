    @Test
    public void shouldEscapeOneIdentifier() {
        Expression escaped = i("beer-sample");
        assertEquals("`beer-sample`", escaped.toString());
    }

    @Test
    public void shouldEscapedMultipleIdentifiers() {
        Expression escaped = i("beer-sample", "someothersample", "third-sample");
        assertEquals("`beer-sample`, `someothersample`, `third-sample`", escaped.toString());
    }

    @Test
    public void shouldEscapeIdentifierInFromClause() {
        Statement escapedFrom = Select.select("*").from(i("test"));
        assertEquals("SELECT * FROM `test`", escapedFrom.toString());
    }
