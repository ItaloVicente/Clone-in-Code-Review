  @Override
  protected void addOperation(final String key, final Operation o) {
    MemcachedNode placeIn = null;
    MemcachedNode primary = locator.getPrimary(key);

    if (primary == null) {
      o.cancel();
      cf.checkConfigUpdate();
      return;
    }

    if(!primary.isActive()) {
      cf.checkConfigUpdate();
    }

    if (primary.isActive() || failureMode == FailureMode.Retry) {
      placeIn = primary;
    } else if (failureMode == FailureMode.Cancel) {
      o.cancel();
    } else {
      for (Iterator<MemcachedNode> i = locator.getSequence(key); placeIn == null
          && i.hasNext();) {
        MemcachedNode n = i.next();
        if (n.isActive()) {
          placeIn = n;
        }
      }

      if (placeIn == null) {
        placeIn = primary;
        this.getLogger().warn(
            "Could not redistribute "
                + "to another node, retrying primary node for %s.", key);
      }
    }

    assert o.isCancelled() || placeIn != null : "No node found for key " + key;
    if (placeIn != null) {
      addOperation(placeIn, o);
    } else {
      assert o.isCancelled() : "No node found for " + key
          + " (and not immediately cancelled)";
    }
  }

