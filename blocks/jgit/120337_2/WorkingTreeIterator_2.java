	protected DirCacheIterator getDirCacheIterator() {
		if (state.dirCacheTree >= 0 && state.walk != null) {
			return state.walk.getTree(state.dirCacheTree
					DirCacheIterator.class);
		}
		return null;
	}

	public void setWalkIgnoredDirectories(boolean includeIgnored) {
		state.walkIgnored = includeIgnored;
	}

	public boolean walksIgnoredDirectories() {
		return state.walkIgnored;
	}

