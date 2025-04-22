	private Set<String> cleanPath(String path
			throws IOException {
		File curFile = new File(repo.getWorkTree()
		if (curFile.isDirectory()) {
			if (directories) {
				if (new File(curFile
					if (force) {
						if (!dryRun) {
							FileUtils.delete(curFile
						}
					}
				} else {
					if (!dryRun) {
						FileUtils.delete(curFile
					}
				}
			}
		} else {
			if (!dryRun) {
				FileUtils.delete(curFile
			}
			inFiles.add(path);
		}

		return inFiles;
	}

