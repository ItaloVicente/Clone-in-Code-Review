    public void shouldCreateRawExpression() {
        Expression expr = x("foobar");
        assertEquals("foobar", expr.toString());

        expr = x(42);
        assertEquals("42", expr.toString());

        expr = x(Long.MAX_VALUE);
        assertEquals("9223372036854775807", expr.toString());

        expr = x(42.143);
        assertEquals("42.143", expr.toString());

        expr = x(Double.MAX_VALUE);
        assertEquals("1.7976931348623157E308", expr.toString());

        expr = x(true);
        assertEquals("TRUE", expr.toString());

        expr = x(false);
        assertEquals("FALSE", expr.toString());

        expr = x(JsonObject.create().put("foo", "bar"));
        assertEquals("{\"foo\":\"bar\"}", expr.toString());

        expr = x(JsonArray.create().add(true).add(123).add("hello"));
        assertEquals("[true,123,\"hello\"]", expr.toString());
    }

    @Test
    public void shouldCreateLiteralExpressions() {
        assertEquals("NULL", Expression.NULL().toString());
        assertEquals("FALSE", Expression.FALSE().toString());
        assertEquals("TRUE", Expression.TRUE().toString());
        assertEquals("MISSING", Expression.MISSING().toString());
    }

    @Test
    public void shouldEscapeIdentifiers() {
