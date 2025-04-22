
    @Test
    public void testQueueColl() {
        CouchbaseQueue<String> queue = new CouchbaseQueue<String>(ctx.bucket(), "dsqueueColl", String.class);
        queue.add("val1");
        queue.add("val2");
        assertEquals("val1", queue.remove());
        assertEquals("val2", queue.remove());
        ctx.bucket().remove("dsqueueColl");
    }

