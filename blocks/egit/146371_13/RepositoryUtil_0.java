	@Nullable
	public String getRelativizedRepositoryPath(
			final @NonNull Repository repository) {
		File dir = repository.getDirectory();
		if (dir == null) {
			return null;
		}
		return relativizeToWorkspace(dir.getAbsolutePath());
	}

