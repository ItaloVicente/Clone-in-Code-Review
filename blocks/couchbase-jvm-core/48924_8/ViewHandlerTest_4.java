        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);
        assertFalse(inbound.status().isSuccess());
        assertEquals(ResponseStatus.NOT_EXISTS, inbound.status());

        List<ByteBuf> rows = inbound.rows().toList().toBlocking().single();
        assertTrue(rows.isEmpty());

        String error = inbound.error().toBlocking().single();
        assertEquals("{\"errors\":[{\"error\":\"not_found\",\"reason\":\"Design document _design/designdoc not found\"}]}", error);
