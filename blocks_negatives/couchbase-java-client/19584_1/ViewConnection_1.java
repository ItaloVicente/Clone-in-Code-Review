      nodesToShutdown.addAll(oddNodes);
    } catch (IOException e) {
      getLogger().error("Connection reconfiguration failed", e);
    } finally {
      reconfiguring = false;
    }
  }

  /**
   * Infinitely loop processing IO.
   */
  @Override
  public void run() {
    while (running) {
      if (!reconfiguring) {
