		if (gitDirAbsolutePath == null) {
			if (container != null) {
				IPath cloc = container.getLocation();
				if (cloc != null)
					gitDirAbsolutePath = cloc.append(getGitDirPath());
			}
		}
