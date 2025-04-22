        assertEquals(1, eventSink.responseEvents().size());
        BucketConfigResponse event = (BucketConfigResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.NOT_EXISTS, event.status());
        assertEquals("Not found.", event.config());
        assertTrue(requestQueue.isEmpty());
