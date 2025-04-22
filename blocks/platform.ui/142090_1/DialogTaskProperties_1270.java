			Object done = attributes.get(IMarker.DONE);
			return done != null && done instanceof Boolean
					&& ((Boolean) done).booleanValue();
		}
		return marker.getAttribute(IMarker.DONE, false);
	}

	protected int getPriority() {
		IMarker marker = getMarker();
		int priority = IMarker.PRIORITY_NORMAL;
		if (marker == null) {
