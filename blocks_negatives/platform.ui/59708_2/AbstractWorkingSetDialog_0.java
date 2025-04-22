		IWorkingSetManager manager = WorkbenchPlugin.getDefault()
				.getWorkingSetManager();
		IWorkingSet editWorkingSet = (IWorkingSet) getSelectedWorkingSets()
				.get(0);
		IWorkingSetEditWizard wizard = manager
				.createWorkingSetEditWizard(editWorkingSet);
