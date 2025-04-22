  public boolean cancel() {
    assert op != null : "No operation";
    op.cancel();
    return op.getState() == OperationState.WRITE_QUEUED;
  }

