    if(reconfiguring) {
      getLogger().debug("Suppressing attempt to reconfigure again while "
        + "reconfiguring.");
      return;
    }

