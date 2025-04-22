  @Override
  protected void finishedPayload(byte[] pl) throws IOException {
    if (keyLen > 0) {
      final byte[] keyBytes = new byte[keyLen];
      final byte[] data = new byte[pl.length - keyLen];
      System.arraycopy(pl, 0, keyBytes, 0, keyLen);
      System.arraycopy(pl, keyLen, data, 0, pl.length - keyLen);
      Callback cb = (Callback) getCallback();
      cb.gotStat(new String(keyBytes, "UTF-8"), new String(data, "UTF-8"));
    } else {
      getCallback().receivedStatus(STATUS_OK);
      transitionState(OperationState.COMPLETE);
    }
    resetInput();
  }
