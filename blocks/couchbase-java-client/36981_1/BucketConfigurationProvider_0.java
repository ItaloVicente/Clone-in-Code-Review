    long configRevision = config.getRevision();
    if (configRevision > 0) {
      if (configRevision > lastRevision) {
        lastRevision = configRevision;
      } else {
        return;
      }
    }
