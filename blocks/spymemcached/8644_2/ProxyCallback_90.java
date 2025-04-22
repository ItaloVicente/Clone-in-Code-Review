  private static final OperationStatus END = new OperationStatus(true, "END");

  private boolean completed = false;
  private int remainingKeys = 0;
  private GetOperation.Callback cb = null;

  public GetCallbackWrapper(int k, GetOperation.Callback c) {
    super();
    remainingKeys = k;
    cb = c;
  }

  public void gotData(String key, int flags, byte[] data) {
    assert !completed : "Got data for a completed wrapped op";
    cb.gotData(key, flags, data);
    if (--remainingKeys == 0) {
      receivedStatus(END);
    }
  }

  public void receivedStatus(OperationStatus status) {
    if (!completed) {
      cb.receivedStatus(status);
    }
  }

  public void complete() {
    assert !completed;
    cb.complete();
    completed = true;
  }
}
