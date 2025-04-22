
    @Test
    public void test57() {
        Statement statement = selectDistinctRaw("books.authorName").from("A").as("books").join("A")
                .as("authors").useHash(HashSide.PROBE).on(x("books.authorName").eq(x("authors.name")))
                .where(x("books.type").eq(s("book")).and(x("authors.type").eq(s("author"))));

        assertEquals("SELECT DISTINCT RAW books.authorName FROM A AS books JOIN A AS authors USE HASH(PROBE) ON " +
                "books.authorName = authors.name WHERE books.type = \"book\" AND authors.type = \"author\"", statement.toString());
    }

    @Test
    public void test58() {
        Statement statement = selectDistinctRaw("books.authorName").from("A").as("books").join("A")
                .as("authors").useHash(HashSide.BUILD).on(x("books.authorName").eq(x("authors.name")))
                .where(x("books.type").eq(s("book")).and(x("authors.type").eq(s("author"))));

        assertEquals("SELECT DISTINCT RAW books.authorName FROM A AS books JOIN A AS authors USE HASH(BUILD) ON " +
                "books.authorName = authors.name WHERE books.type = \"book\" AND authors.type = \"author\"", statement.toString());
    }

    @Test
    public void test59() {
        Statement statement = selectDistinctRaw("books.authorName").from("A").as("books").join("A")
                .as("authors").useNestedLoop().on(x("books.authorName").eq(x("authors.name"))).where(x("books.type").eq(s("book")).and(x("authors.type").eq(s("author"))));

        assertEquals("SELECT DISTINCT RAW books.authorName FROM A AS books JOIN A AS authors USE NL ON " +
                "books.authorName = authors.name WHERE books.type = \"book\" AND authors.type = \"author\"", statement.toString());
    }
