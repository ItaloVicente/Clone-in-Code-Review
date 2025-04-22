    MemcachedNode primary = locator.getPrimary(key);
    addOperation(key, o, primary);
  }

  public void addOperation(final String key, final Operation o,
          final MemcachedNode node) {
