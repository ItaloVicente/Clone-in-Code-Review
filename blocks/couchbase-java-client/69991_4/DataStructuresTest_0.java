    @Test
    public void testMapCollection() {
        CouchbaseMap<String> map = new CouchbaseMap<String>(ctx.bucket(), "dsmapColl", String.class);
        map.add("foo", "bar");
        String val = map.get("foo");
        assertEquals(val, "bar");
        boolean result = map.remove("foo");
        assertEquals(result, true);
        result = map.remove("foo");
        assertEquals(result, true);
        int size = map.size();
        map.add("foo", "bar", MutationOptionBuilder.builder().persistTo(PersistTo.MASTER));
        int newSize = map.size();
        assert (newSize == size + 1);
        result = map.add("foo", null);
        assertEquals(result, true);
        ctx.bucket().remove("dsmapColl");
    }

