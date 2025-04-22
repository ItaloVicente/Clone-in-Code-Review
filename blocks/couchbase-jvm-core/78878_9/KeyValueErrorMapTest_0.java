    private static void startRetryVerifyRequest() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("idx", "0");
        parameters.put("bucket", bucket());
        String rawBody = sendGetHttpRequestToMock("mock/start_retry_verify", parameters);
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

    private static void checkRetryVerifyRequest(long code, byte opcode, int fuzz) throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("idx", "0");
        parameters.put("opcode", Byte.toString(opcode));
        parameters.put("errcode", Long.toString(code));
        parameters.put("bucket", bucket());
        parameters.put("fuzz_ms",  Integer.toString(fuzz));
        String rawBody = sendGetHttpRequestToMock("mock/check_retry_verify", parameters);
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

    private static void opFailRequest(long code, int count) throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("code", Long.toString(code));
        parameters.put("count", Integer.toString(count));
        parameters.put("servers", "[0]");
        String rawBody = sendGetHttpRequestToMock("mock/opfail", parameters);
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        String verifyStatus = respObject.get("status").getAsString();
        if (verifyStatus.compareTo("ok") != 0) {
            throw new Exception(respObject.get("error").getAsString());
        }
    }

