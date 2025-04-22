    public void testExistsIn() {
        assertTrue(ctx.bucket().existsIn(key, "sub"));
        assertTrue(ctx.bucket().existsIn(key, "int"));
        assertTrue(ctx.bucket().existsIn(key, "string"));
        assertTrue(ctx.bucket().existsIn(key, "array"));
        assertTrue(ctx.bucket().existsIn(key, "boolean"));
        assertFalse(ctx.bucket().existsIn(key, "somePathBlaBla"));
