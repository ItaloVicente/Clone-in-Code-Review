  public void testOps() throws Exception {
    CouchbaseClient mc = null;
    try {
      mc = new CouchbaseClient(Arrays.asList(base), "default", "");
    } catch (IOException ex) {
      fail(ex.getMessage());
    } catch (ConfigurationException ex) {
      fail(ex.getMessage());
    } catch (URISyntaxException ex) {
      fail(ex.getMessage());
    }
