	public Optional<String> name(IMarker marker) {
		if (!marker.exists()) {
			return Optional.empty();
		}
		try {
			Object name = marker.getAttribute(MarkerItemDefaults.NAME_ATTRIBUTE);
			if (name != null) {
				return Optional.of(name.toString());
			}
		} catch (CoreException e) {
			reporter.accept(e);
		}
		return Optional.of(marker.getResource().getName());
	}
