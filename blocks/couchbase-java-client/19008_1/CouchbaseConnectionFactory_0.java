  private synchronized void resubConfigUpdate() {
    LOGGER.log(Level.INFO, "Attempting to resubscribe for cluster config"
      + " updates.");
    resubExec.execute(new Resubscriber());


  }

