        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map found = mapper.readValue(response, Map.class);
                            assertEquals(12, found.size());
                            assertEquals("San Francisco", found.get("city"));
                            assertEquals("United States", found.get("country"));
                            Map geo = (Map) found.get("geo");
                            assertNotNull(geo);
                            assertEquals(3, geo.size());
                            assertEquals("ROOFTOP", geo.get("accuracy"));
                        } catch (IOException e) {
                            fail("no result expected");
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
