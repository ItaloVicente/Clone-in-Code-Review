
      long now = System.currentTimeMillis();
      long intervalWaited = now - this.configProviderLastUpdateTimestamp;
      if (intervalWaited < this.getMinReconnectInterval()) {
        LOGGER.log(Level.FINE, "Ignoring config update check.  Only {0}ms out"
                + " of a threshold of {1}ms since last update.",
                new Object[]{intervalWaited, this.getMinReconnectInterval()});
        return;
      }

