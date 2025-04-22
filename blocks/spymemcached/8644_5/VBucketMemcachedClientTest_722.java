  public void testOps() throws Exception {
    MembaseClient mc = null;
    try {
      URI base = new URI("http://" + TestConfig.IPV4_ADDR + ":8091/pools");
      mc = new MembaseClient(Arrays.asList(base), "default", "Administrator",
          "password");
    } catch (IOException ex) {
      Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(
          Level.SEVERE, null, ex);
    } catch (ConfigurationException ex) {
      Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(
          Level.SEVERE, null, ex);
    } catch (URISyntaxException ex) {
      Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(
          Level.SEVERE, null, ex);
