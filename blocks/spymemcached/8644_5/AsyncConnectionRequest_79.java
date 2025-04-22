  private volatile boolean completed;
  private volatile NHttpClientConnection conn;

  public AsyncConnectionRequest() {
    super();
  }

  public boolean isCompleted() {
    return this.completed;
  }

  public void setConnection(NHttpClientConnection newConn) {
    if (this.completed) {
      return;
    }
    this.completed = true;
    synchronized (this) {
      this.conn = newConn;
      notifyAll();
    }
  }

  public NHttpClientConnection getConnection() {
    return this.conn;
  }

  public void cancel() {
    if (this.completed) {
      return;
    }
    this.completed = true;
    synchronized (this) {
      notifyAll();
    }
  }
