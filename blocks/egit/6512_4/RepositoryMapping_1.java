				+ format(containerPathString)
				+ " -> '" //$NON-NLS-1$
				+ format(gitDirPathString)
				+ "', absolute path: '"  //$NON-NLS-1$
				+ format(absolutePath) + "' ]"; //$NON-NLS-1$
	}

	private String format(Object o) {
		if (o == null)
			return "<null>"; //$NON-NLS-1$
		else if (o.toString().length() == 0)
			return "<empty>"; //$NON-NLS-1$
		else
			return o.toString();
