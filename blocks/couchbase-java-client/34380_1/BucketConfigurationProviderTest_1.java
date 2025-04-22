  @Test
  public void shouldSkipBinaryOnManualDisable() throws Exception {
    if (!isCCCPAware) {
      LOGGER.info("Skipping Test because cluster is not CCCP aware.");
      return;
    }
    System.setProperty("cbclient.disableCarrierBootstrap", "true");
