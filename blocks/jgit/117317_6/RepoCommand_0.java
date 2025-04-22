		checkCallable();
		if (baseUri == null) {
		}
		if (inputStream == null) {
			if (manifestPath == null || manifestPath.length() == 0)
				throw new IllegalArgumentException(
						JGitText.get().pathNotConfigured);
