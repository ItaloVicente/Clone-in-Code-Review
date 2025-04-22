    if(reconfiguring) {
      getLogger().debug("Suppressing attempt to reconfigure again while still "
        + "reconfiguring.");
      return;
    }

