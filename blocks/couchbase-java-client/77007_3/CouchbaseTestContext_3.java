        protected int getCarrierPortInfo(int httpPort) throws Exception {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost("localhost").setPort(httpPort).setPath("mock/get_mcports")
                    .setParameter("bucket", this.bucketName);
            HttpGet request = new HttpGet(builder.build());
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status < 200 || status > 300) {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
            String rawBody = EntityUtils.toString(response.getEntity());
            com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
            com.google.gson.JsonArray portsArray = respObject.getAsJsonArray("payload");
            return portsArray.get(0).getAsInt();
        }

        protected void createMock() {
            int nodeCount = Integer.parseInt(testProperties.getProperty("mock.nodeCount", "1"));
            int replicaCount = Integer.parseInt(testProperties.getProperty("mock.replicaCount", "1"));
            String bucketType = testProperties.getProperty("mock.bucketType", "couchbase");

            BucketConfiguration bucketConfiguration = new BucketConfiguration();
            bucketConfiguration.numNodes = nodeCount;
            bucketConfiguration.numReplicas = replicaCount;
            bucketConfiguration.numVBuckets = 1024;
            bucketConfiguration.name = this.bucketName;
            bucketConfiguration.type =  bucketType.compareToIgnoreCase("couchbase") == 0 ? com.couchbase.mock.Bucket.BucketType.COUCHBASE: com.couchbase.mock.Bucket.BucketType.MEMCACHED;
            bucketConfiguration.password = this.bucketPassword;
            ArrayList<BucketConfiguration> configList = new ArrayList<BucketConfiguration>();
            configList.add(bucketConfiguration);
            try {
                this.couchbaseMock = new CouchbaseMock(0, configList);
                this.couchbaseMock.start();
                this.couchbaseMock.waitForStartup();
            } catch (Exception ex) {
                throw new RuntimeException("Unable to initialize mock" + ex.getMessage(), ex);
            }
        }

