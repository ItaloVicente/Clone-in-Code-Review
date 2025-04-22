        assertFalse(inbound.status().isSuccess());
        assertEquals(ResponseStatus.FAILURE, inbound.status());

        inbound.info().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf buf) {
                String response = buf.toString(CharsetUtil.UTF_8);
                try {
                    Map found = mapper.readValue(response, Map.class);
                    assertEquals(5, found.size());
                    assertEquals(new Integer(4100), found.get("code"));
                    assertEquals("Parse Error", found.get("message"));
                } catch (IOException e) {
                    assertFalse(true);
                }
            }
        });
