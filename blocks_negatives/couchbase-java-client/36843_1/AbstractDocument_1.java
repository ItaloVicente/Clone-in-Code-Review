  private String id;
  private long cas;
  private int expiry;
  private T content;

  protected AbstractDocument() {
    this(null, null, 0, 0);
  }

  protected AbstractDocument(String id) {
    this(id, null, 0, 0);
  }

  protected AbstractDocument(String id, T content) {
    this(id, content, 0, 0);
  }

  protected AbstractDocument(String id, T content, int expiry) {
    this(id, content, 0, expiry);
  }

  protected AbstractDocument(String id, T content, long cas) {
    this(id, content, cas, 0);
  }
