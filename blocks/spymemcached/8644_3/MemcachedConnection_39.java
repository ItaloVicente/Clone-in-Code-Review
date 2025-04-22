        getLogger().debug("Shut down channel %s", qa.getChannel());
      }
    }
    running = false;
    selector.close();
    getLogger().debug("Shut down selector %s", selector);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{MemcachedConnection to");
    for (MemcachedNode qa : locator.getAll()) {
      sb.append(" ");
      sb.append(qa.getSocketAddress());
