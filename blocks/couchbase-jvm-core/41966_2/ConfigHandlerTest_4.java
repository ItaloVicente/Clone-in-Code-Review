        assertEquals(1, eventSink.responseEvents().size());
        BucketConfigResponse event = (BucketConfigResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.FAILURE, event.status());
        assertEquals("Unauthorized", event.config());
        assertTrue(requestQueue.isEmpty());
