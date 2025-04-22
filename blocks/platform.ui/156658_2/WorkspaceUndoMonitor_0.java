	private boolean isAffectedBy(IResourceDelta delta) {
		boolean[] result = new boolean[] { false };
		try {
			delta.accept(d -> {
				if (result[0]) {
					return false;
				}
				if (d.getKind() != IResourceDelta.CHANGED) {
					result[0] = true;
					return false;
				}
				int flags = d.getFlags();
				if (flags != 0 && flags != IResourceDelta.MARKERS) {
					result[0] = true;
					return false;
				}
				return true;
			});
		} catch (CoreException e) {
		}
		return result[0];
	}

