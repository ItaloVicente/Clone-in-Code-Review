		allowAnyDeletes = canDelete;
		if (!canDelete)
			allowBranchDeletes = false;
	}

	public boolean isAllowBranchDeletes() {
		return allowBranchDeletes;
	}

	public void setAllowBranchDeletes(boolean canDelete) {
		allowBranchDeletes = allowAnyDeletes && canDelete;
