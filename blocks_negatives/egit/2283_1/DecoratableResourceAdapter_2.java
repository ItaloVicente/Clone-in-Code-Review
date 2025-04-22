
	public String getName() {
		return resource.getName();
	}

	public int getType() {
		return resource.getType();
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getBranch() {
		return branch;
	}

	public boolean isTracked() {
		return tracked;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public boolean isDirty() {
		return dirty;
	}

	public Staged staged() {
		return staged;
	}

	public boolean hasConflicts() {
		return conflicts;
	}

	public boolean isAssumeValid() {
		return assumeValid;
	}
