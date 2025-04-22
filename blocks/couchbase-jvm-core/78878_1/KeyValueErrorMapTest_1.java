    private static void startRetryVerifyRequest() throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPort(mock().getHttpPort()).setPath("mock/start_retry_verify")
                .setParameter("idx", "0")
                .setParameter("bucket", bucket());
        HttpGet request = new HttpGet(builder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        String rawBody = EntityUtils.toString(response.getEntity());
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

    private static void checkRetryVerifyRequest(long code) throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPort(mock().getHttpPort()).setPath("mock/check_retry_verify")
                .setParameter("idx", "0")
                .setParameter("opcode", "1")
                .setParameter("errcode", Long.toString(code))
                .setParameter("bucket", bucket());
        HttpGet request = new HttpGet(builder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        String rawBody = EntityUtils.toString(response.getEntity());
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

    private static void opFailRequest(long code, int count) throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPort(mock().getHttpPort()).setPath("mock/opfail")
                .setParameter("code", Long.toString(code))
                .setParameter("count", Integer.toString(count))
                .setParameter("servers", "[0]");
        HttpGet request = new HttpGet(builder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        String rawBody = EntityUtils.toString(response.getEntity());
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

