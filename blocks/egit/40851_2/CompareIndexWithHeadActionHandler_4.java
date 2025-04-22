
	private boolean isStaged(Repository repository, IPath location,
			boolean checkIndex) {
		if (location == null || location.toFile().isDirectory()) {
			return false;
		}
