  @Override
  protected void decodePayload(byte[] pl) {
    StoreOperation.Callback gcb = (StoreOperation.Callback) getCallback();
    gcb.gotData(key, cas);
    getCallback().receivedStatus(STATUS_OK);
  }

