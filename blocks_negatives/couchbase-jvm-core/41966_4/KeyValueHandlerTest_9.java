        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GetBucketConfigResponse inbound = (GetBucketConfigResponse) firedEvents.get(0);
        assertEquals(ResponseStatus.SUCCESS, inbound.status());
        assertEquals("bucket", inbound.bucket());
        assertEquals(InetAddress.getLocalHost(), inbound.hostname());
        assertEquals("content", inbound.content().toString(CharsetUtil.UTF_8));
        assertTrue(queue.isEmpty());
