  protected void checkState() {
    if (shutDown) {
      throw new IllegalStateException("Shutting down");
    }
    assert isAlive() : "IO Thread is not running.";
  }

  @Override
  public void run() {
    while (running) {
      if (!reconfiguring) {
        try {
          handleIO();
        } catch (IOException e) {
          logRunException(e);
        } catch (CancelledKeyException e) {
          logRunException(e);
        } catch (ClosedSelectorException e) {
          logRunException(e);
        } catch (IllegalStateException e) {
          logRunException(e);
        }
      }
    }
    getLogger().info("Shut down memcached client");
  }

  private void logRunException(Exception e) {
    if (shutDown) {
      getLogger().debug("Exception occurred during shutdown", e);
    } else {
      getLogger().warn("Problem handling memcached IO", e);
    }
  }
