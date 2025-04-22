  public void setCas(long inCas) {
    this.cas = inCas;
  }

  public long getCas() {
    if (isDone() && getStatus().isSuccess()
            && (cas == null)) {
      throw new UnsupportedOperationException("ASCII Protocol"
              + " does not return a CAS value");
    }
    return cas;
  }
