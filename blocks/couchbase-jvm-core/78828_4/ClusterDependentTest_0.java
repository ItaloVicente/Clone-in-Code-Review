    private static int[] minClusterVersion() throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(seedNode).setPort(8091).setPath("/pools/default/buckets/" + bucket)
            .setParameter("bucket", bucket);
        HttpGet request = new HttpGet(builder.build());
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(seedNode, 8091), new UsernamePasswordCredentials(adminUser, adminPassword));
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status > 300) {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        String rawConfig = EntityUtils.toString(response.getEntity());
        return minNodeVersionFromConfig(rawConfig);
    }


    public static void connect(boolean useMock) throws Exception {

        if (minClusterVersion()[0] >= 5) {
            username = adminUser;
            password = adminPassword;
        }

