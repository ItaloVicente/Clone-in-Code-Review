      if (doingResubscribe.compareAndSet(false, true)) {
        getConfigurationProvider().signalOutdated();
      } else {
        LOGGER.log(Level.CONFIG, "Duplicate resubscribe for config updates"
          + " suppressed.");
      }
