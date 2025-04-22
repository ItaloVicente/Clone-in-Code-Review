        assertEquals(1, eventSink.responseEvents().size());
        GetBucketConfigResponse event = (GetBucketConfigResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(BUCKET, event.bucket());
        assertEquals(ResponseStatus.SUCCESS, event.status());
        assertEquals(InetAddress.getLocalHost(), event.hostname());
        assertEquals("content", event.content().toString(CHARSET));
