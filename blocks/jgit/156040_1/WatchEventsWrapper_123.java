	public Path getWatchable() {
		if (watchable == null) {
			return null;
		}
		try {
			return Paths.get(watchable);
		} catch (Exception e) {
			return null;
		}
	}
