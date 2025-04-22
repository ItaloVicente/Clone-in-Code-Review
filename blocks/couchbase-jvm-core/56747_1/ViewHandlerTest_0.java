    @Test
    public void shouldParseRowWithBracketInIdKeysAndValue() throws Exception {
        String response = Resources.read("query_brackets.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);

        assertTrue(inbound.status().isSuccess());
        assertEquals(200, inbound.responseCode());
        assertEquals("OK", inbound.responsePhrase());

        ByteBuf singleRow = inbound.rows().toBlocking().single(); //single will blow up if not exactly one
        String singleRowData = singleRow.toString(CharsetUtil.UTF_8);
        Map found = null;
        try {
            found = mapper.readValue(singleRowData, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed parsing JSON on data " + singleRowData);
        }

        assertEquals(3, found.size());
        assertTrue(found.containsKey("id"));
        assertTrue(found.containsKey("key"));
        assertTrue(found.containsKey("value"));
        assertEquals("IdClosing}BracketId", found.get("id"));
        assertEquals(Arrays.asList("KeyClosing}BracketKey", "KeySquareClosing]SquareBracketKey"), found.get("key"));
        assertEquals("ValueClosing}BracketValue", found.get("value"));

        final AtomicInteger called = new AtomicInteger();
        inbound.info().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                called.incrementAndGet();
                assertEquals("{\"total_rows\":1}", byteBuf.toString(CharsetUtil.UTF_8));
            }
        });
        assertEquals(1, called.get());
    }

