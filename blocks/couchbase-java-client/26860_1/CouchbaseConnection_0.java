    if(reconfiguring) {
      getLogger().debug("Suppressing attemt to reconfigure again while still "
        + "reconfiguring!");
      return;
    }

