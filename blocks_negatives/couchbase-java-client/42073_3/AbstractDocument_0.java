  protected AbstractDocument(String id, int expiry, T content, long cas) {
    this.id = id;
    this.cas = cas;
    this.expiry = expiry;
    this.content = content;
  }
