
  public boolean isNotUpdating() {
    return isNotUpdating;
  }

  public final void setIsNotUpdating() {
    this.isNotUpdating = true;
    LOGGER.finest("Marking bucket as not updating,"
      + " disconnected from config stream");
  }
