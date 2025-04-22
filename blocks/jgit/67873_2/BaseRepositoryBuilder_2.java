
		if (getGitCommonDir() == null) {
			File commonDirFile = new File(getGitDir()
			if (commonDirFile.isFile()) {
				String commonDirPath = new String(IO.readFully(commonDirFile)).trim();
				gitCommonDir = new File(commonDirPath);
				if (!gitCommonDir.isAbsolute()) {
					gitCommonDir = new File(getGitDir()
							.getCanonicalFile();
				}
			}
		}
