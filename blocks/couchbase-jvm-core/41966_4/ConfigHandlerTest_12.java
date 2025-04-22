
        assertEquals(1, eventSink.responseEvents().size());
        BucketStreamingResponse event = (BucketStreamingResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.NOT_EXISTS, event.status());
        assertNull(event.configs());
        assertNotNull(event.host());
        assertEquals(0, requestQueue.size());
