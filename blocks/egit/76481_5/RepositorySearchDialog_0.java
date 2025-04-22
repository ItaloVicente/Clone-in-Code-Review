	private boolean suppressed(@NonNull File root, @NonNull File resolved) {
			return !allowBare && !Constants.DOT_GIT.equals(resolved.getName())
				&& isSameFile(root, resolved);
	}

	private boolean isSameFile(@NonNull File f1, @NonNull File f2) {
		try {
			return Files.isSameFile(f1.toPath(), f2.toPath());
		} catch (IOException e) {
			return false;
		}
	}

