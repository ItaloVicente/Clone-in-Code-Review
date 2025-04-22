	public CloneCommand setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public CloneCommand setCloneAllBranches(boolean cloneAllBranches) {
		this.cloneAllBranches = cloneAllBranches;
		return this;
	}

	public CloneCommand setBranchesToClone(Collection<String> branchesToClone) {
		this.branchesToClone = branchesToClone;
		return this;
	}

