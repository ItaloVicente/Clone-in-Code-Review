  protected void notifyDisconnected() {
    this.bucket.setIsNotUpdating();
    setChanged();
    LOGGER.log(Level.FINE, "Marked bucket " + this.bucket.getName()
      + " as not updating.  Notifying observers.");
    LOGGER.log(Level.FINER, "There appear to be " + this.countObservers()
      + " observers waiting for notification");
    notifyObservers(this.bucket);
  }

