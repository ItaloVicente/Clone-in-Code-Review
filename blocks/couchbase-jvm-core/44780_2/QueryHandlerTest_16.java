        final AtomicInteger found = new AtomicInteger(0);
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf row) {
                        found.incrementAndGet();
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
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 5)
        );
        assertEquals(5, found.get());
