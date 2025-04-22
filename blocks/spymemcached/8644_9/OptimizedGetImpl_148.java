  private final ProxyCallback pcb;

  public OptimizedGetImpl(GetOperation firstGet) {
    super(Collections.<String>emptySet(), new ProxyCallback());
    pcb = (ProxyCallback) getCallback();
    addOperation(firstGet);
  }

  public void addOperation(GetOperation o) {
    pcb.addCallbacks(o);
    for (String k : o.getKeys()) {
      addKey(k);
      setVBucket(k, ((VBucketAware) o).getVBucket(k));
    }
  }

  public int size() {
    return pcb.numKeys();
  }
