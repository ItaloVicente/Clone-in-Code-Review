	public GitModelObject(GitModelObject parent) {
		this.parent = parent;
	}

	public GitModelObject getParent() {
		return parent;
	}

	public abstract int repositoryHashCode();

