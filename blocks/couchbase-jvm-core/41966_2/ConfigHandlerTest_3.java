        assertEquals(1, eventSink.responseEvents().size());
        BucketConfigResponse event = (BucketConfigResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.SUCCESS, event.status());
        assertEquals("foobar", event.config());
        assertTrue(requestQueue.isEmpty());
