        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        BucketStreamingResponse inbound = (BucketStreamingResponse) firedEvents.get(0);

        assertEquals(ResponseStatus.SUCCESS, inbound.status());
        assertNotNull(inbound.configs());
        assertNotNull(inbound.host());
        assertEquals(0, queue.size());
