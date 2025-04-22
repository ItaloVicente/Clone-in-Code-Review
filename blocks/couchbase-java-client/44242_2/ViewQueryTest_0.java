    @Test
    public void shouldRespectDevelopmentParam() {
        ViewQuery query = ViewQuery.from("design", "view").development(true);
        assertTrue(query.isDevelopment());

        query = ViewQuery.from("design", "view").development(false);
        assertFalse(query.isDevelopment());
    }

