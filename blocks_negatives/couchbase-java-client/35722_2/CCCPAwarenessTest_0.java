  public static void checkCCCPAwareness() throws Exception {
    CouchbaseClient client = new CouchbaseClient(
      Arrays.asList(new URI(SERVER_URI)),
      "default",
      ""
    );
