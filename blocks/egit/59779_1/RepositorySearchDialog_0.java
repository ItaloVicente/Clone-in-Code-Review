		for (File foundDir : directories) {
			String absolutePath = foundDir.getAbsolutePath();
			if (!fExistingDirectories.contains(absolutePath)
					&& !fExistingDirectories.contains(FileUtils
							.canonicalize(foundDir).getAbsolutePath())) {
				validDirs.add(absolutePath);
