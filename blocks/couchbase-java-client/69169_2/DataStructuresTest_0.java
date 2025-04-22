
    @Test
    public void testList() {
        ctx.bucket().async().listPush("dslist", "foo").toBlocking().single();
        String myval = ctx.bucket().async().listGet("dslist", 1, String.class).toBlocking().single();
        assertEquals(myval, "foo");
        ctx.bucket().async().listShift("dslist", null).toBlocking().single();
        assertNull(ctx.bucket().async().listGet("dslist", 0, Object.class).toBlocking().single());
        ctx.bucket().async().listSet("dslist", 1, JsonArray.create().add("baz")).toBlocking().single();
        JsonArray array = ctx.bucket().async().listGet("dslist", 1, JsonArray.class).toBlocking().single();
        assertEquals(array.get(0), "baz");
        ctx.bucket().async().listSet("dslist", 1, JsonObject.create().put("foo", "bar")).toBlocking().single();
        JsonObject object = ctx.bucket().async().listGet("dslist", 1, JsonObject.class).toBlocking().single();
        assertEquals(object.get("foo"), "bar");
        int size = ctx.bucket().async().listSize("dslist").toBlocking().single();
        assert (size > 0);
        ctx.bucket().async().listRemove("dslist", 1).toBlocking().single();
        int newSize = ctx.bucket().async().listSize("dslist").toBlocking().single();
        assertEquals(size - 1, newSize);
        while (newSize > 1) {
            ctx.bucket().async().listRemove("dslist", 1).toBlocking().single();
            newSize = ctx.bucket().async().listSize("dslist").toBlocking().single();
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListGetInvalidIndex() {
        ctx.bucket().async().listGet("dslist", -99999, Object.class).toBlocking().single();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListSetInvalidIndex() {
        ctx.bucket().async().listSet("dslist", -10, "bar").toBlocking().single();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListRemoveInvalidIndex() {
        ctx.bucket().async().listGet("dslist", -99999, Object.class).toBlocking().single();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListRemoveNonExistentIndex() {
        ctx.bucket().async().listGet("dslist", 2, Object.class).toBlocking().single();
    }

    @Test(expected = IllegalStateException.class)
    public void testListPushFullException() {
        for (int i = 0; i < 5; i++) {
            char[] data = new char[5000000];
            String str = new String(data);
            boolean result = ctx.bucket().async().listPush("dslistFull", str, MutationOptionBuilder.build().withDurability(PersistTo.MASTER)).toBlocking().single();
            assertEquals(result, true);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testListShiftFullException() {
        for (int i = 0; i < 5; i++) {
            char[] data = new char[5000000];
            String str = new String(data);
            boolean result = ctx.bucket().async().listShift("dslistFull", str, MutationOptionBuilder.build().withDurability(PersistTo.MASTER)).toBlocking().single();
            assertEquals(result, true);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testListSetFullException() {
        for (int i = 0; i < 5; i++) {
            char[] data = new char[5000000];
            String str = new String(data);
            boolean result = ctx.bucket().async().listSet("dslistFull", 0, str, MutationOptionBuilder.build().withDurability(PersistTo.MASTER)).toBlocking().single();
            assertEquals(result, true);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testSyncListAddTimeout() {
        ctx.bucket().listPush("dslist", "timeout", 1, TimeUnit.NANOSECONDS);
    }
