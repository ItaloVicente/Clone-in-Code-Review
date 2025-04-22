      redistributeOperations(notCompletedOperations);
    }
  }

  private void redistributeOperations(Collection<HttpOperation> ops) {
    int added = 0;
    for (HttpOperation op : ops) {
      addOp(op);
      added++;
