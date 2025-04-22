		WorkingSetEditWizard editWizard = new WorkingSetEditWizard(editPage);
		editWizard.setSelection(workingSet);
		return editWizard;
	}

	@Deprecated
	@Override
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(Shell parent) {
		return createWorkingSetSelectionDialog(parent, true);
	}
