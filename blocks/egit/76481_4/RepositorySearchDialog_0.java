	private boolean suppressed(File root, File resolved) {
			return !allowBare && !Constants.DOT_GIT.equals(resolved.getName())
				&& isSameFile(root, resolved);
	}

	private boolean isSameFile(File f1, File f2) {
		try {
			return Files.isSameFile(f1.toPath(), f2.toPath());
		} catch (IOException e) {
			return false;
		}
	}

