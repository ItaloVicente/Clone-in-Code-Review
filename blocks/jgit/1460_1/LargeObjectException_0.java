	protected String getObjectName() {
		if (getObjectId() != null)
			return getObjectId().name();
		return JGitText.get().unknownObject;
	}

