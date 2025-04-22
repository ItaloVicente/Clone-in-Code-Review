	@Override
	public boolean isTracked() {
		return tracked;
	}

	@Override
	public boolean isIgnored() {
		return ignored;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public Staged staged() {
		return staged;
	}

	@Override
	public boolean hasConflicts() {
		return conflicts;
	}

	@Override
	public boolean isAssumeValid() {
		return assumeValid;
	}
