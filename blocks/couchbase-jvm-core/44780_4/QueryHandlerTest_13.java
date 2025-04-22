        assertResponse(inbound, false, ResponseStatus.FAILURE, FAKE_REQUESTID, FAKE_CLIENTID, "fatal",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        fail("no result expected");
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map error = mapper.readValue(response, Map.class);
                            assertEquals(5, error.size());
                            assertEquals(new Integer(4100), error.get("code"));
                            assertEquals(Boolean.FALSE, error.get("temp"));
                            assertEquals("Parse Error", error.get("msg"));
                        } catch (IOException e) {
                            fail();
                        }
                    }
                },
                expectedMetricsCounts(1, 0)
        );
