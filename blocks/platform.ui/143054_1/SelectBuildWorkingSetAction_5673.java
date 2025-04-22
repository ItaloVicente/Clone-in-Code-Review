	private IWorkingSet queryForWorkingSet() {
		IWorkingSetManager manager = window.getWorkbench()
				.getWorkingSetManager();
		IWorkingSetSelectionDialog dialog = manager
				.createWorkingSetSelectionDialog(window.getShell(), false);
		dialog.open();
		IWorkingSet[] sets = dialog.getSelection();
		if (sets == null || sets.length == 0) {
