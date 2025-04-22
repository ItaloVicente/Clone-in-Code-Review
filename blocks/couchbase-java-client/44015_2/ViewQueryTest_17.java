
    @Test(expected = IllegalArgumentException.class)
    public void shouldDisallowNegativeLimit() {
        ViewQuery.from("design", "view").limit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDisallowNegativeSkip() {
        ViewQuery.from("design", "view").skip(-1);
    }

    @Test
    public void shouldToggleDevelopment() {
        ViewQuery query = ViewQuery.from("design", "view").development(true);
        assertTrue(query.isDevelopment());

        query = ViewQuery.from("design", "view").development(false);
        assertFalse(query.isDevelopment());
    }

