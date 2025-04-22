	private boolean suppressed(File root, File resolved) {
		try {
			return suppressBare
					&& Files.isSameFile(root.toPath(), resolved.toPath());
		} catch (IOException e) {
			return false;
		}
	}

