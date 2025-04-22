        assertTrue(inbound.status().isSuccess());
        assertEquals(ResponseStatus.SUCCESS, inbound.status());

        List<ByteBuf> found = inbound.rows().toList().toBlocking().single();
        assertEquals(5, found.size());
        for (ByteBuf row : found) {
            String content = row.toString(CharsetUtil.UTF_8);
            assertNotNull(content);
            assertTrue(!content.isEmpty());
            try {
                Map decoded = mapper.readValue(content, Map.class);
                assertTrue(decoded.size() > 0);
                assertTrue(decoded.containsKey("name"));
            } catch(Exception e) {
                assertTrue(false);
            }
        }

        final AtomicInteger invokeCounter2 = new AtomicInteger();
        inbound.info().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf buf) {
                invokeCounter2.incrementAndGet();
                String response = buf.toString(CharsetUtil.UTF_8);
                try {
                    Map found = mapper.readValue(response, Map.class);
                    assertEquals(4, found.size());
                    assertTrue(found.containsKey("code"));
                    assertTrue(found.containsKey("key"));
                } catch (IOException e) {
                    assertFalse(true);
                }
            }
        });

        assertEquals(2, invokeCounter2.get());
