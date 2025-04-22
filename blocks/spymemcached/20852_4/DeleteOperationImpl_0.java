  @Override
  protected void decodePayload(byte[] pl) {
    super.decodePayload(pl);
    ((DeleteOperation.Callback) getCallback()).gotData(responseCas);
  }

