        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);
        assertTrue(inbound.status().isSuccess());

        List<ByteBuf> rows = inbound.rows().toList().toBlocking().single();
        assertTrue(rows.isEmpty());

        String error = inbound.error().toBlocking().single();
        List<Object> parsed = mapper.readValue(error, List.class);
        assertEquals(1, parsed.size());
