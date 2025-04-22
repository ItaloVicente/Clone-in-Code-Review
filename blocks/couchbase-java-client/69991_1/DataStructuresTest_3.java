
    @Test
    public void testSetColl() {
        CouchbaseSet<String> set = new CouchbaseSet<String>(ctx.bucket(), "dssetColl", String.class);
        set.add("foo");
        assertEquals(false, set.add("foo"));
        set.remove("foo");
        assertEquals(true, set.add("foo"));
        assertEquals(true, set.exists("foo"));
        assertEquals(1, set.size());
        ctx.bucket().remove("dssetColl");
    }
