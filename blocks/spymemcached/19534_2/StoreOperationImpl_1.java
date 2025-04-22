  @Override
  protected void decodePayload(byte[] pl) {
    super.decodePayload(pl);
    ((StoreOperation.Callback) getCallback()).gotData(key, responseCas);
  }

