		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered != null) {
			return filtered.length > 0;
		}
		Boolean foundAny = (Boolean) foundAnyCache.get(parent);
		if (foundAny == null) {
			foundAny = computeAnyVisible(viewer, elements) ? Boolean.TRUE : Boolean.FALSE;
			foundAnyCache.put(parent, foundAny);
		}
		return foundAny.booleanValue();
	}
