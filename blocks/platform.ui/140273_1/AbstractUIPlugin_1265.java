	}

	private IPath getStateLocationOrNull() {
		try {
			return getStateLocation();
		} catch (IllegalStateException e) {
			return null;
		}
	}
