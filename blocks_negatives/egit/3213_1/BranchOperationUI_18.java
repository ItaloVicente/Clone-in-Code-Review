	private String createDisplayedRef() {
		if (refName != null)
			refToCheckOut = refName;
		else if (commitId != null)
			refToCheckOut = commitId.getName().substring(0, 7)+ "... "; //$NON-NLS-1$
		else
			throw new IllegalStateException(
		return refToCheckOut;
	}
