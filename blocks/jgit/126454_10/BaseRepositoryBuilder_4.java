		}

		File gitDirFile = new File(getGitDir()
		if (gitDirFile.isFile()) {
			String workDirPath = new String(IO.readFully(gitDirFile)).trim();
			File workTreeDotGitFile = new File(workDirPath);
			if (!workTreeDotGitFile.isAbsolute()) {
				workTreeDotGitFile = new File(getGitDir()
						.getCanonicalFile();
			}
			if (workTreeDotGitFile != null) {
				return workTreeDotGitFile.getParentFile();
			}
		}
