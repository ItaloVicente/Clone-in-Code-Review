    if (!running) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }
    running = false;

