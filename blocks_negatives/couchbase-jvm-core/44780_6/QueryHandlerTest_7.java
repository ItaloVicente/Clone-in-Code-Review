
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
