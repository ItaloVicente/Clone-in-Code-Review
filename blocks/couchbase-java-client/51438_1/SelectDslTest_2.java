    @Test
    public void testJoins() {
        Expression eToken = Expression.x("a");
        String sToken = "a";

        String pathString = new DefaultLetPath(null).join(sToken).toString();
        String pathExpression = new DefaultLetPath(null).join(eToken).toString();
        assertEquals("JOIN a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftJoin(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftJoin(eToken).toString();
        assertEquals("LEFT JOIN a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).innerJoin(sToken).toString();
        pathExpression = new DefaultLetPath(null).innerJoin(eToken).toString();
        assertEquals("INNER JOIN a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftOuterJoin(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftOuterJoin(eToken).toString();
        assertEquals("LEFT OUTER JOIN a", pathString);
        assertEquals(pathString, pathExpression);
    }

    @Test
    public void testNests() {
        Expression eToken = Expression.x("a");
        String sToken = "a";

        String pathString = new DefaultLetPath(null).nest(sToken).toString();
        String pathExpression = new DefaultLetPath(null).nest(eToken).toString();
        assertEquals("NEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftNest(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftNest(eToken).toString();
        assertEquals("LEFT NEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).innerNest(sToken).toString();
        pathExpression = new DefaultLetPath(null).innerNest(eToken).toString();
        assertEquals("INNER NEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftOuterNest(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftOuterNest(eToken).toString();
        assertEquals("LEFT OUTER NEST a", pathString);
        assertEquals(pathString, pathExpression);
    }

    @Test
    public void testUnnests() {
        Expression eToken = Expression.x("a");
        String sToken = "a";

        String pathString = new DefaultLetPath(null).unnest(sToken).toString();
        String pathExpression = new DefaultLetPath(null).unnest(eToken).toString();
        assertEquals("UNNEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftUnnest(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftUnnest(eToken).toString();
        assertEquals("LEFT UNNEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).innerUnnest(sToken).toString();
        pathExpression = new DefaultLetPath(null).innerUnnest(eToken).toString();
        assertEquals("INNER UNNEST a", pathString);
        assertEquals(pathString, pathExpression);

        pathString = new DefaultLetPath(null).leftOuterUnnest(sToken).toString();
        pathExpression = new DefaultLetPath(null).leftOuterUnnest(eToken).toString();
        assertEquals("LEFT OUTER UNNEST a", pathString);
        assertEquals(pathString, pathExpression);
    }

