	static boolean isInteresting(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.CHANGED
				&& (delta.getFlags() & INTERESTING_CHANGES) == 0) {
			return false;
		}
		return true;
	}

