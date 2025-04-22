    @Test
    public void shouldInjectAdvancedScoreSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(sortScore(), sortScore().descending(true));
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(
                JsonObject.create().put("by", "score"),
                JsonObject.create().put("by", "score").put("descending", true)
            ));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectAdvancedIdSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(sortId(), sortId().descending(true));
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(
                JsonObject.create().put("by", "id"),
                JsonObject.create().put("by", "id").put("descending", true)
            ));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectAdvancedFieldSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(sortField("fieldname"), sortField("f")
                .missing(FieldMissing.FIRST).mode(FieldMode.DEFAULT).type(FieldType.AUTO)
                .descending(true));
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(
                JsonObject.create().put("by", "field").put("field", "fieldname"),
                JsonObject.create().put("by", "field").put("field", "f")
                    .put("descending", true).put("mode", "default").put("missing", "first")
                    .put("type", "auto")
            ));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectAdvancedGeoSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(sortGeo(1.0, 2.0, "fname")
                .unit("km").descending(true));
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(
                JsonObject.create().put("by", "geo_distance").put("field", "fname")
                    .put("descending", true).put("unit", "km")
                    .put("location", JsonArray.from(1.0, 2.0))
            ));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectMixedSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort("_score", sortId().descending(true), "bar");
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(
                "_score",
                JsonObject.create().put("by", "id").put("descending", true),
                "bar"
            ));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectRawSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(JsonObject.create().put("by", "new_stuff"));
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from(JsonObject.create().put("by", "new_stuff")));
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAdvancedSortOnUnsupportedType() {
        SearchQuery p = new SearchQuery(null, null)
            .sort(123);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);
    }

