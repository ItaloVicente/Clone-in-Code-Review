	private boolean suppressed(File root, File resolved) {
		try {
			return suppressBare && !resolved.getName().equals(Constants.DOT_GIT)
					&& Files.isSameFile(root.toPath(), resolved.toPath());
		} catch (IOException e) {
			return false;
		}
	}

