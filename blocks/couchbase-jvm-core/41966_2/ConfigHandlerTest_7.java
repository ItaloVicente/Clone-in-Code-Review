        assertEquals(1, eventSink.responseEvents().size());
        FlushResponse event = (FlushResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.FAILURE, event.status());
        assertEquals("{\"_\":\"Flush is disabled for the bucket\"}", event.content());
        assertTrue(requestQueue.isEmpty());
