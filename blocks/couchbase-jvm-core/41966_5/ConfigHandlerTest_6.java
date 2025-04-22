        assertEquals(1, eventSink.responseEvents().size());
        FlushResponse event = (FlushResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.SUCCESS, event.status());
        assertEquals("OK", event.content());
        assertTrue(requestQueue.isEmpty());
