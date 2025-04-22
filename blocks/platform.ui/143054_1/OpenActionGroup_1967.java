		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		boolean anyResourceSelected = !selection.isEmpty() && ResourceSelectionUtil.allResourcesAreOfType(selection,
				IResource.PROJECT | IResource.FOLDER | IResource.FILE);
		boolean onlyFilesSelected = !selection.isEmpty()
				&& ResourceSelectionUtil.allResourcesAreOfType(selection, IResource.FILE);

		if (onlyFilesSelected) {
			openFileAction.selectionChanged(selection);
			menu.add(openFileAction);
			fillOpenWithMenu(menu, selection);
		}

		if (anyResourceSelected) {
			addNewWindowAction(menu, selection);
		}
	}

	private void fillOpenWithMenu(IMenuManager menu, IStructuredSelection selection) {

		if (selection.size() != 1) {
