	@Override
	public boolean hasUnstagedChanges() {
		if (isIgnored()) {
			return false;
		}
		return !isTracked() || isDirty() || isMissing() || hasConflicts();
	}

