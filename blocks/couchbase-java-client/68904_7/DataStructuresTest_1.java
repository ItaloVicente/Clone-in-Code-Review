    @Test(expected = IllegalStateException.class)
    public void testQueueEmptyRemove() {
        int size = ctx.bucket().async().queueSize("dsqueue").toBlocking().single();
        while(size > 0) {
            ctx.bucket().async().queueRemove("dsqueue", Object.class).toBlocking().single();
            size = ctx.bucket().async().queueSize("dsqueue").toBlocking().single();
        }
        ctx.bucket().async().queueRemove("dsqueue", Object.class).toBlocking().single();
    }

