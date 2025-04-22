      Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
        "Invalid client configuration received from server. Staying with "
        + "existing configuration.", ex);
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINE,
        "Invalid client configuration received:\n" + handler.getLastResponse()
        + "\n");
