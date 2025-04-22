  public AuthThread(MemcachedConnection c, OperationFactory o,
      AuthDescriptor a, MemcachedNode n) {
    conn = c;
    opFact = o;
    authDescriptor = a;
    node = n;
    start();
  }
