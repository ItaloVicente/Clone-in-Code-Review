	private String createDisplayedRef() {
		String refToCheckOut = ""; //$NON-NLS-1$
		if (refName != null)
			refToCheckOut = refName;
		else if (commitId != null)
			refToCheckOut = commitId.getName().substring(0, 7)+ "... "; //$NON-NLS-1$
		else
			throw new IllegalStateException(
					"Either refName or commitId must be non-null"); //$NON-NLS-1$
		return refToCheckOut;
	}

