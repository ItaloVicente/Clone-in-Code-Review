    public void shouldWrapWithStringQuotes() {
        Expression quoted = s("foobar");
        assertEquals("\"foobar\"", quoted.toString());

        quoted = s("foo", "bar");
        assertEquals("\"foo\", \"bar\"", quoted.toString());
