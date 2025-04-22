	private void updateCache(Composite composite, boolean flushCache) {
		Control[] children = composite.getChildren();
		if (flushCache) {
			cache.flush();
		}
		cache.setControls(children);
	}

