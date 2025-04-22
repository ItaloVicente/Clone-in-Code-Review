    boolean shutdownResult = false;
    try {
      shutdownResult = super.shutdown(timeout, unit);
      CouchbaseConnectionFactory cf = (CouchbaseConnectionFactory) connFactory;
      cf.getConfigurationProvider().shutdown();
      vconn.shutdown();
    } catch (IOException ex) {
      Logger.getLogger(
         CouchbaseClient.class.getName()).log(Level.SEVERE, null, ex);
    }
