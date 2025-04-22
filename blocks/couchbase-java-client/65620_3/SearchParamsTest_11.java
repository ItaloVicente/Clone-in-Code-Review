    @Test
    public void shouldHaveHighlightButNoStyleWhenServerDefault() {
        SearchQuery p = new SearchQuery(null, null)
            .highlight(HighlightStyle.SERVER_DEFAULT);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("highlight", JsonObject.empty());
        assertEquals(expected, result);
    }

    @Test
    public void testHighlightWithoutParamHasServerDefaultStyle() {
        SearchQuery a = new SearchQuery(null, null)
            .highlight(HighlightStyle.SERVER_DEFAULT);
        SearchQuery b = new SearchQuery(null, null).highlight();

        JsonObject resultA = JsonObject.create();
        a.injectParams(resultA);
        JsonObject resultB = JsonObject.create();
        b.injectParams(resultB);

        assertEquals(resultA, resultB);
    }

    @Test
    public void testHighlightWithFieldsOnlyHasServerDefaultStyle() {
        SearchQuery a = new SearchQuery(null, null)
            .highlight(HighlightStyle.SERVER_DEFAULT, "foo", "bar");
        SearchQuery b = new SearchQuery(null, null).highlight("foo", "bar");

        JsonObject resultA = JsonObject.create();
        a.injectParams(resultA);
        JsonObject resultB = JsonObject.create();
        b.injectParams(resultB);
        JsonObject expected = JsonObject.create()
            .put("highlight", JsonObject.create().put("fields", JsonArray.from("foo", "bar")));

        assertEquals(expected, resultA);
        assertEquals(resultA, resultB);
    }

