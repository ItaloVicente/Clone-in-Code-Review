    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectAtPlusWithBadFullVector() {
        QueryParams.build().consistencyAtPlus(new int[] { 1, 2, 3});
    }


    @Test
    public void shouldProduceSparseVectorWithAtPlusMap() {
        QueryParams p = QueryParams.build()
            .consistencyAtPlus(Collections.singletonMap("5", 123));

        JsonObject actual = JsonObject.empty();
        p.injectParams(actual);

        assertEquals("at_plus", actual.getString("scan_consistency"));
        assertTrue(actual.get("scan_vector") instanceof JsonObject);
        assertEquals(1, actual.getObject("scan_vector").size());
        assertEquals(123, actual.getObject("scan_vector").get("5"));
    }

    @Test
    public void shouldProduceFullVectorWithAtPlusArray() {
        int[] vector = new int[1024];
        Arrays.fill(vector, 22);
        QueryParams p = QueryParams.build().consistencyAtPlus(vector);

        JsonObject actual = JsonObject.empty();
        p.injectParams(actual);

        assertEquals("at_plus", actual.getString("scan_consistency"));
        assertTrue(actual.get("scan_vector") instanceof JsonArray);
        assertEquals(1024, actual.getArray("scan_vector").size());
        assertEquals(22, actual.getArray("scan_vector").get(13));
    }

