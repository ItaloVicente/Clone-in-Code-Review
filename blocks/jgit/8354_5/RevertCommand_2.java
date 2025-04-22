	public RevertCommand setOurCommitName(String ourCommitName) {
		this.ourCommitName = ourCommitName;
		return this;
	}

	private String calculateOurName(Ref headRef) {
		if (ourCommitName != null)
			return ourCommitName;

		String targetRefName = headRef.getTarget().getName();
		String headName = Repository.shortenRefName(targetRefName);
		return headName;
	}

