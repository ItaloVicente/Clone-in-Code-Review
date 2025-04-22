	private String getTarget() {
		if (refName != null)
			return refName;
		return commitId.name();
	}

