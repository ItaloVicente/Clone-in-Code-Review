        assertEquals(1, eventSink.responseEvents().size());
        BucketStreamingResponse event = (BucketStreamingResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.SUCCESS, event.status());
        assertNotNull(event.configs());
        assertNotNull(event.host());
