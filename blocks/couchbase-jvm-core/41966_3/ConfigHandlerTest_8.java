        assertEquals(1, eventSink.responseEvents().size());
        GetDesignDocumentsResponse event = (GetDesignDocumentsResponse) eventSink.responseEvents().get(0).getMessage();

        assertEquals(ResponseStatus.SUCCESS, event.status());
        assertEquals("foobar", event.content());
        assertTrue(requestQueue.isEmpty());
