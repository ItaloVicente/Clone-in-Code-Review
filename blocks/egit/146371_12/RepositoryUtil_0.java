	@Nullable
	public String getRelativizedWorkspacePath(@NonNull
	final Repository repository) {
		File dir = repository.getDirectory();
		if (dir == null) {
			return null;
		}
		return relativizeToWorkspace(dir.getAbsolutePath());
	}

