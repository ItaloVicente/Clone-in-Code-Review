					if (dirty != null) {
						dirty.remove(resource);
					}
				}
				cache.putAll(severities);
				if (dirty != null) {
					dirty.clear();
				} else {
					markDirty(Collections.emptySet());
