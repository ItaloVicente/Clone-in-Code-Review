    @Test
    public void testGetHeaderFieldsAllowMultipleValues() throws MalformedURLException {
        HttpResponse responseMock = new HttpResponseMock();
        String headerField = "WWW-Authenticate";
        responseMock.addHeader(headerField, "Basic");
        responseMock.addHeader(headerField, "Digest");
        responseMock.addHeader(headerField, "NTLM");
        connection.resp = responseMock;
        List<String> headerValues = connection.getHeaderFields().get(headerField);
        assertEquals(3, headerValues.size());
        assertTrue(headerValues.contains("Basic"));
        assertTrue(headerValues.contains("Digest"));
        assertTrue(headerValues.contains("NTLM"));
    }

    private class HttpResponseMock extends AbstractHttpMessage implements HttpResponse {
        @Override
        public StatusLine getStatusLine() {
            return null;
        }

        @Override
        public void setStatusLine(StatusLine statusLine) {

        }

        @Override
        public void setStatusLine(ProtocolVersion protocolVersion, int i) {

        }

        @Override
        public void setStatusLine(ProtocolVersion protocolVersion, int i, String s) {

        }

        @Override
        public void setStatusCode(int i) throws IllegalStateException {

        }

        @Override
        public void setReasonPhrase(String s) throws IllegalStateException {

        }

        @Override
        public HttpEntity getEntity() {
            return null;
        }

        @Override
        public void setEntity(HttpEntity httpEntity) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public void setLocale(Locale locale) {

        }

        @Override
        public ProtocolVersion getProtocolVersion() {
            return null;
        }
    }
