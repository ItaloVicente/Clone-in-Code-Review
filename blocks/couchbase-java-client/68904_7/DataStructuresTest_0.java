    @Test(expected = IndexOutOfBoundsException.class)
    public void testListRemoveInvalidIndex() {
        ctx.bucket().async().listGet("dslist", -99999, Object.class).toBlocking().single();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListRemoveNonExistentIndex() {
        ctx.bucket().async().listGet("dslist", 2, Object.class).toBlocking().single();
    }

	@Test
