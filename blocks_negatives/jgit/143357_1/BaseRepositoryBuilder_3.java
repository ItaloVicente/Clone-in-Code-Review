		if (getGitDir() == null) {
			String val = sr.getenv(GIT_DIR_KEY);
			if (val != null)
				setGitDir(new File(val));
		}

		if (getObjectDirectory() == null) {
			String val = sr.getenv(GIT_OBJECT_DIRECTORY_KEY);
			if (val != null)
				setObjectDirectory(new File(val));
		}

		if (getAlternateObjectDirectories() == null) {
			String val = sr.getenv(GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY);
			if (val != null) {
				for (String path : val.split(File.pathSeparator))
					addAlternateObjectDirectory(new File(path));
			}
		}

		if (getWorkTree() == null) {
			String val = sr.getenv(GIT_WORK_TREE_KEY);
			if (val != null)
				setWorkTree(new File(val));
		}

		if (getIndexFile() == null) {
			String val = sr.getenv(GIT_INDEX_FILE_KEY);
			if (val != null)
				setIndexFile(new File(val));
		}

		if (ceilingDirectories == null) {
			String val = sr.getenv(GIT_CEILING_DIRECTORIES_KEY);
			if (val != null) {
				for (String path : val.split(File.pathSeparator))
					addCeilingDirectory(new File(path));
			}
		}

