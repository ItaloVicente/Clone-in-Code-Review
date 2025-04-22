
    @Test
    public void test55() {
        Statement statement = selectDistinctRaw("name").from("authors");

        assertEquals("SELECT DISTINCT RAW name FROM authors", statement.toString());
    }

    @Test
    public void test56() {
        Statement statement = selectDistinctRaw(x("name")).from("authors");

        assertEquals("SELECT DISTINCT RAW name FROM authors", statement.toString());
    }
