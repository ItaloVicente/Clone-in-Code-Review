	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(Shell parent) {
		return createWorkingSetSelectionDialog(parent, true);
	}

	@Override
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(Shell parent, boolean multi) {
		return createWorkingSetSelectionDialog(parent, multi, null);
	}

