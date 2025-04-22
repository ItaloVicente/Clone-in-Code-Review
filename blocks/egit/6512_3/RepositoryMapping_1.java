				+ nullEmptyOrString(containerPathString)
				+ " -> '" //$NON-NLS-1$
				+ nullEmptyOrString(gitDirPathString)
				+ "', absolute path: '" + getGitDirAbsolutePath() + "' ]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private String nullEmptyOrString(String s) {
		if (s == null)
			return "<null>"; //$NON-NLS-1$
		else if (s.length() == 0)
			return "<empty>"; //$NON-NLS-1$
		else
			return s;
