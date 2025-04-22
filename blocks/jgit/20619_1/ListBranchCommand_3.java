
	public ListBranchCommand setContains(String containsCommitish) {
		checkCallable();
		this.containsCommitish = containsCommitish;
		return this;
	}

	private Collection<Ref> getRefs(String prefix) throws IOException {
		return repo.getRefDatabase().getRefs(prefix).values();
	}
