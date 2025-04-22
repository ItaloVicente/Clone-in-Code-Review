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
