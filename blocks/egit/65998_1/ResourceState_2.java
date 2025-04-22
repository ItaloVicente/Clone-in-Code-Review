	@Override
	public final boolean hasUnstagedChanges() {
		if (isIgnored()) {
			return false;
		}
		return !isTracked() || isDirty() || isMissing() || hasConflicts();
	}

