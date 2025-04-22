
    @Test
    public void shouldDecodeRawQueryResponseAsSingleJson() throws Exception {
        String response = Resources.read("chunked.json", this.getClass());
        String[] chunks = new String[] {
                response.substring(0, 48),
                response.substring(48, 84),
                response.substring(84, 144),
                response.substring(144, 258),
                response.substring(258, 438),
                response.substring(438, 564),
                response.substring(564, 702),
                response.substring(702, 740),
                response.substring(740)
        };

        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        Object[] httpChunks = new Object[chunks.length + 1];
        httpChunks[0] = responseHeader;
        for (int i = 1; i <= chunks.length; i++) {
            String chunk = chunks[i - 1];
            if (i == chunks.length) {
                httpChunks[i] = new DefaultLastHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            } else {
                httpChunks[i] = new DefaultHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            }
        }

        Subject<CouchbaseResponse,CouchbaseResponse> obs = AsyncSubject.create();
        RawQueryRequest requestMock = mock(RawQueryRequest.class);
        when(requestMock.observable()).thenReturn(obs);
        queue.add(requestMock);
        channel.writeInbound(httpChunks);
        RawQueryResponse inbound = (RawQueryResponse) obs.timeout(1, TimeUnit.SECONDS).toBlocking().last();
        String jsonResponse = inbound.jsonResponse().toString(CharsetUtil.UTF_8);
        inbound.jsonResponse().release();

        assertNotNull(inbound);
        assertEquals(ResponseStatus.SUCCESS, inbound.status());
        assertEquals(200, inbound.httpStatusCode());
        assertEquals("OK", inbound.httpStatusMsg());

        assertEquals(response, jsonResponse);
    }

    @Test
    public void shouldDecodeRawQueryServerNotOk() throws Exception {
        int expectedCode = 400; //BAD REQUEST
        String expectedMsg = "Sorry Sir!";
        String body = "Something bad happened, and this is not JSON";

        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(expectedCode, expectedMsg));
        HttpContent responseBody = new DefaultLastHttpContent(Unpooled.copiedBuffer(body, CharsetUtil.UTF_8));

        Subject<CouchbaseResponse,CouchbaseResponse> obs = AsyncSubject.create();
        RawQueryRequest requestMock = mock(RawQueryRequest.class);
        when(requestMock.observable()).thenReturn(obs);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseBody);
        RawQueryResponse inbound = (RawQueryResponse) obs.timeout(1, TimeUnit.SECONDS).toBlocking().last();

        String byteBufResponse = inbound.jsonResponse().toString(CharsetUtil.UTF_8);
        inbound.jsonResponse().release();

        assertNotNull(inbound);
        assertEquals(ResponseStatus.INVALID_ARGUMENTS, inbound.status());
        assertEquals(expectedCode, inbound.httpStatusCode());
        assertEquals(expectedMsg, inbound.httpStatusMsg());

        assertEquals(body, byteBufResponse); //the response still contains the body
    }
