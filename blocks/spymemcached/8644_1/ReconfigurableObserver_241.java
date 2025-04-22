  private final Reconfigurable rec;

  public ReconfigurableObserver(Reconfigurable rec) {
    this.rec = rec;
  }

  public void update(Observable o, Object arg) {
    rec.reconfigure((Bucket) arg);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
