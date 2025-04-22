	public int getType() {
		return IResource.FILE;
	}

	public String getName() {
		return null;
	}

	public String getRepositoryName() {
		return null;
	}

	public String getBranch() {
		return null;
	}

	public String getBranchStatus() {
		return null;
	}

	public boolean isTracked() {
		return state != State.UNTRACKED;
	}

	public boolean isIgnored() {
		return false;
	}

	public boolean isDirty() {
		return state == State.MODIFIED || state == State.PARTIALLY_MODIFIED;
	}

	public Staged staged() {
		switch (state) {
		case ADDED:
			return Staged.ADDED;
		case CHANGED:
			return Staged.MODIFIED;
		case REMOVED:
			return Staged.REMOVED;
		case MISSING:
			return Staged.REMOVED;
		default:
			return Staged.NOT_STAGED;
		}
	}

	public boolean hasConflicts() {
		return state == State.CONFLICTING;
	}

	public boolean isAssumeValid() {
		return false;
	}

