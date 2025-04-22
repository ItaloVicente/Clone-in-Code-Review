    @Test
    public void testListCollection() {
        CouchbaseList<String> list = new CouchbaseList<String>(ctx.bucket(), "dslistColl", String.class);
        list.add("foo");
        String val = list.get(0);
        assertEquals(val, "foo");
        list.set(1, "bar");
        assertEquals(list.get(1), "bar");
        int size = list.size();
        assert (size > 0);
        list.remove(1);
        int newSize = list.size();
        assertEquals(size - 1, newSize);
        ctx.bucket().remove("dslistColl");
    }

