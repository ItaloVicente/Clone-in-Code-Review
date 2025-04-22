	private void createWorkingSetGroup(Composite workArea) {
		String[] workingSetTypes = new String[] {
			"org.eclipse.ui.resourceWorkingSetPage", //$NON-NLS-1$
			"org.eclipse.jdt.ui.JavaWorkingSetPage" //$NON-NLS-1$
		};
		workingSetGroup = new WorkingSetGroup(workArea, null, workingSetTypes);
	}

