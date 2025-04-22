    @Test
    public void shouldWrapExpressionInParenthesis() {
        Expression base = x("foo").eq(x("bar"));

        assertEquals("( foo = bar )", par(base).toString());
    }

