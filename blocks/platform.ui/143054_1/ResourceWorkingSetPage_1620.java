		IResource[] members = null;
		try {
			members = container.members();
		} catch (CoreException ex) {
			handleCoreException(ex, getShell(), IDEWorkbenchMessages.ResourceWorkingSetPage_error,
					IDEWorkbenchMessages.ResourceWorkingSetPage_error_updateCheckedState);
		}
		for (int i = members.length - 1; i >= 0; i--) {
			IResource element = members[i];
			boolean elementGrayChecked = tree.getGrayed(element) || tree.getChecked(element);
