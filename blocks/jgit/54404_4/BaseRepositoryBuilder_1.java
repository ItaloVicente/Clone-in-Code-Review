
		if (getGitCommonDir() == null) {
			File commonDirFile = new File(getGitDir()
			if (commonDirFile.isFile()) {
				String commonDirPath = new String(IO.readFully(commonDirFile)).trim();
				if (!new File(commonDirPath).isAbsolute())
					gitCommonDir = new File(gitDir
				else
					gitCommonDir = new File(commonDirPath);
			}
		}
