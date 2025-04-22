
	private void cleanup() {
		try {
			if (directory != null) {
				if (!directoryExistsInitially) {
					FileUtils.delete(directory
							| FileUtils.SKIP_MISSING | FileUtils.IGNORE_ERRORS);
				} else {
					deleteChildren(directory);
				}
			}
			if (gitDir != null) {
				if (!gitDirExistsInitially) {
					FileUtils.delete(gitDir
							| FileUtils.SKIP_MISSING | FileUtils.IGNORE_ERRORS);
				} else {
					deleteChildren(directory);
				}
			}
		} catch (IOException e) {
		}
	}

	private void deleteChildren(File file) throws IOException {
		if (!FS.DETECTED.isDirectory(file)) {
			return;
		}
		for (File child : file.listFiles()) {
			FileUtils.delete(child
					| FileUtils.IGNORE_ERRORS);
		}
	}
