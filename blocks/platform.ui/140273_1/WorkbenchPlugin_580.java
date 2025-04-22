	}

	public IPath getDataLocation() {
		try {
			return getStateLocation();
		} catch (IllegalStateException e) {
