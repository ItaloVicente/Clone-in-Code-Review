		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered == null) {
			Boolean foundAny = (Boolean) foundAnyCache.get(parent);
			if (foundAny != null && !foundAny.booleanValue()) {
				filtered = EMPTY;
			} else {
				filtered = super.filter(viewer, parent, elements);
			}
			cache.put(parent, filtered);
		}
		return filtered;
	}
