  public boolean cancel(boolean ign) {
    boolean rv = false;
    for (Operation op : ops) {
      rv |= op.getState() == OperationState.WRITING;
      op.cancel();
    }
    for (Future<T> v : rvMap.values()) {
      v.cancel(ign);
