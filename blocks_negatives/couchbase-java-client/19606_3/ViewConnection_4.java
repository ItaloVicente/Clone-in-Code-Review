  /**
   * Infinitely loop processing IO.
   */
  @Override
  public void run() {
    while (running) {
      if (!reconfiguring) {
