		TreeViewer treeView = navigator.getViewer();
		Shell shell = treeView.getControl().getShell();
		workingSetGroup = new WorkingSetFilterActionGroup(shell, workingSetUpdater);
		workingSetGroup.setWorkingSet(navigator.getWorkingSet());
		sortAndFilterGroup = new SortAndFilterActionGroup(navigator);
		workspaceGroup = new WorkspaceActionGroup(navigator);
