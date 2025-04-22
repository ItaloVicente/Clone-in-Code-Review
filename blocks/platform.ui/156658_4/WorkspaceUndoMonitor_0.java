	private boolean isAffectedBy(IResourceDelta delta) {
		if (delta.getKind() != IResourceDelta.CHANGED) {
			return true;
		}
		int flags = delta.getFlags();
		if (flags != 0 && flags != IResourceDelta.MARKERS) {
			return true;
		}
		IResourceDelta[] children = delta.getAffectedChildren();
		for (IResourceDelta child : children) {
			if (isAffectedBy(child)) {
				return true;
			}
		}
		return false;
	}

