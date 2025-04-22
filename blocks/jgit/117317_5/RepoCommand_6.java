		assert (!repo.isBare());
		assert (git != null);
		if (!linkfiles.isEmpty()) {
			throw new UnsupportedOperationException(
					JGitText.get().nonBareLinkFilesNotSupported);
		}
