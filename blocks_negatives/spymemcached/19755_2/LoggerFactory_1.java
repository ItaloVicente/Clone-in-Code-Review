      Logger newLogger = null;
      try {
        newLogger = getNewInstance(name);
      } catch (Exception e) {
        throw new RuntimeException("Problem getting logger", e);
      }
