    @Test
    public void shouldNegateExpression() {
        Expression expr = x("foobar").not();
        assertEquals("NOT foobar", expr.toString());
    }

    @Test
    public void shouldAndOrCombineExpressions() {
        Expression expr = x("foo").and(x("bar"));
        assertEquals("foo AND bar", expr.toString());

        expr = i("blu-rb").or(x(5));
        assertEquals("`blu-rb` OR 5", expr.toString());
    }

    @Test
    public void shouldCombineExpressionsWithArithmetic() {
        Expression expr = x(5).eq(x(5));
        assertEquals("5 = 5", expr.toString());

        expr = s("foo").ne(s("bar"));
        assertEquals("\"foo\" != \"bar\"", expr.toString());

        expr = x(6).gt(3);
        assertEquals("6 > 3", expr.toString());

        expr = x(3).lt(8);
        assertEquals("3 < 8", expr.toString());

        expr = x(3).gte(3);
        assertEquals("3 >= 3", expr.toString());

        expr = x(8).lte(8);
        assertEquals("8 <= 8", expr.toString());

        expr = x("foo").concat(x("bar"));
        assertEquals("foo || bar", expr.toString());
    }

    @Test
    public void shouldCombineIsExpressions() {
        Expression expr = x("foo").isValued();
        assertEquals("foo IS VALUED", expr.toString());

        expr = x("foo").isNotValued();
        assertEquals("foo IS NOT VALUED", expr.toString());

        expr = x("foo").isNull();
        assertEquals("foo IS NULL", expr.toString());

        expr = x("foo").isNotNull();
        assertEquals("foo IS NOT NULL", expr.toString());

        expr = x("foo").isMissing();
        assertEquals("foo IS MISSING", expr.toString());

        expr = x("foo").isNotMissing();
        assertEquals("foo IS NOT MISSING", expr.toString());
    }

    @Test
    public void shouldCombineVariousOperators() {
        Expression expr = x(5).between(3).and(5);
        assertEquals("5 BETWEEN 3 AND 5", expr.toString());

        expr = x(8).notBetween(x(3)).and(x(5));
        assertEquals("8 NOT BETWEEN 3 AND 5", expr.toString());

        expr = i("firstname").like(s("michael%"));
        assertEquals("`firstname` LIKE \"michael%\"", expr.toString());

        expr = i("firstname").notLike(s("michael%"));
        assertEquals("`firstname` NOT LIKE \"michael%\"", expr.toString());

        expr = x("foo").exists();
        assertEquals("EXISTS foo", expr.toString());

        expr = x("firstname").in(x(JsonArray.from("a", "b", "c")));
        assertEquals("firstname IN [\"a\",\"b\",\"c\"]", expr.toString());

        expr = x("firstname").notIn(x(JsonArray.from("a", "b", "c")));
        assertEquals("firstname NOT IN [\"a\",\"b\",\"c\"]", expr.toString());

        expr = x("firstname").as("fname");
        assertEquals("firstname AS fname", expr.toString());
    }

