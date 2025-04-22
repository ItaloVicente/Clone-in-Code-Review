		try {
			result = super.filter(viewer, parent, elements);
		} finally {
			cachedWorkingSet = null;
		}
		return result;
	}
