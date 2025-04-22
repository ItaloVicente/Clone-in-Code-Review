	public boolean hasOverriddenParents() {
		return this.getClass() != RevCommit.class;
	}

	public void setParents(RevCommit... parents) {
		this.parents = parents;
	}

