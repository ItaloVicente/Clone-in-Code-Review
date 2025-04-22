    @Test
    public void testSelectWithUnionAll() {
        Statement statement = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .unionAll()
            .select(x("a"), x("b"));

        assertEquals("SELECT firstname, lastname UNION ALL SELECT a, b", statement.toString());
    }

    @Test
    public void testSelectWithIntersect() {
        Statement statement = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")))
            .intersect()
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "INTERSECT "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement.toString());
    }

    @Test
    public void testSelectWithIntersectAll() {
        Statement statement = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")))
            .intersectAll()
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "INTERSECT ALL "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement.toString());
    }

    @Test
    public void testSelectWithExcept() {
        Statement statement = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")))
            .except()
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "EXCEPT "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement.toString());
    }

    @Test
    public void testSelectWithExceptAll() {
        Statement statement = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")))
            .exceptAll()
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "EXCEPT ALL "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement.toString());
    }

    @Test
    public void testSelectChainedWithUnion() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "UNION "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.union(statement2).toString());
    }

    @Test
    public void testSelectChainedWithUnionAll() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "UNION ALL "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.unionAll(statement2).toString());
    }

    @Test
    public void testSelectChainedWithIntersect() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "INTERSECT "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.intersect(statement2).toString());
    }

    @Test
    public void testSelectChainedWithIntersectAll() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "INTERSECT ALL "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.intersectAll(statement2).toString());
    }

    @Test
    public void testSelectChainedWithExcept() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "EXCEPT "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.except(statement2).toString());
    }

    @Test
    public void testSelectChainedWithExceptAll() {
        SelectResultPath statement1 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("foo")));

        SelectResultPath statement2 = new DefaultSelectPath(null)
            .select(x("firstname"), x("lastname"))
            .from("foo")
            .where(x("lastname").eq(s("bar")));

        String expected = "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"foo\" "
            + "EXCEPT ALL "
            + "SELECT firstname, lastname "
            + "FROM foo "
            + "WHERE lastname = \"bar\"";

        assertEquals(expected, statement1.exceptAll(statement2).toString());
    }

