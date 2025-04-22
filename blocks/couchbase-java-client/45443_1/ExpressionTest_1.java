    @Test
    public void shouldEscapeOneIdentifier() {
        Expression escaped = Expression.i("beer-sample");
        assertEquals("`beer-sample`", escaped.toString());
    }

    @Test
    public void shouldEscapedMultipleIdentifiers() {
        Expression escaped = Expression.i("beer-sample", "someothersample", "third-sample");
        assertEquals("`beer-sample`, `someothersample`, `third-sample`", escaped.toString());
    }
