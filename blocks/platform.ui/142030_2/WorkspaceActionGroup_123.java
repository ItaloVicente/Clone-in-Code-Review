		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		refreshAction.selectionChanged(selection);
		buildAction.selectionChanged(selection);
		openProjectAction.selectionChanged(selection);
		closeUnrelatedProjectsAction.selectionChanged(selection);
		closeProjectAction.selectionChanged(selection);
	}
