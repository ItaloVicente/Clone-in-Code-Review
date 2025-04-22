
    private void logResponse(HttpResponse response) {
        finerLog("STATUS: " + response.getStatus());
        finerLog("VERSION: " + response.getProtocolVersion());

        if (!response.getHeaderNames().isEmpty()) {
            for (String name : response.getHeaderNames()) {
                for (String value : response.getHeaders(name)) {
                    finerLog("HEADER: " + name + " = " + value);
                }
            }
            finerLog(System.getProperty("line.separator"));
        }

        if (response.getStatus().getCode() == 200 && response.isChunked()) {
            readingChunks = true;
            finerLog("CHUNKED CONTENT {");
