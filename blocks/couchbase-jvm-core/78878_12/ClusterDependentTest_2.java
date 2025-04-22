        return EntityUtils.toString(response.getEntity());
    }

    private static int getCarrierPortInfo() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("idx", "0");
        parameters.put("bucket", bucket());
        String rawBody = sendGetHttpRequestToMock("mock/get_mcports", parameters);
