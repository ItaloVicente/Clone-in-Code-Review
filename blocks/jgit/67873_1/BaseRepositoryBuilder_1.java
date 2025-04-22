		if (getGitCommonDir() == null) {
			String val = sr.getenv(GIT_COMMON_DIR_KEY);
			if (val != null)
				setGitCommonDir(new File(val));
		}

