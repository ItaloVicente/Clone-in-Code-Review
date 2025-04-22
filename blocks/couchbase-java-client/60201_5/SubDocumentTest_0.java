    @Test
    public void testFragmentCanBeAnEntity() {
        ctx.bucket()
                .mutateIn(key)
                .upsert("user", new KeyValueTest.User("frank"), false)
                .remove("sub")
                .remove("array")
                .remove("string")
                .remove("boolean")
                .doMutate();

        JsonObject expected = JsonObject.create()
                .put("user", JsonObject.create().put("firstname", "frank"))
                .put("int", 123);

        assertEquals(expected, ctx.bucket().get(key).content());
