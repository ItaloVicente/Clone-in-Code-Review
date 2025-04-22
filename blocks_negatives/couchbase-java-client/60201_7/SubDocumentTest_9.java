        ctx.bucket().lookupIn(key);
    }

    @Test(expected = NullPointerException.class)
    public void testMultiLookupNullSpecFails() {
        ctx.bucket().lookupIn(key, null);
