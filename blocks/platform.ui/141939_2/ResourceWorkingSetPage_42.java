		if (workingSet == null) {
			throw new IllegalArgumentException("Working set must not be null"); //$NON-NLS-1$
		}
		this.workingSet = workingSet;
		if (getShell() != null && text != null) {
			firstCheck = true;
			initializeCheckedState();
			text.setText(workingSet.getName());
		}
	}

	private void setSubtreeChecked(IContainer container, boolean state, boolean checkExpandedState) {
		if (container.isAccessible() == false
				|| (tree.getExpandedState(container) == false && state && checkExpandedState)) {
			return;
		}
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

			if (state) {
				tree.setChecked(element, true);
				tree.setGrayed(element, false);
			} else {
				tree.setGrayChecked(element, false);
			}
			if (element instanceof IContainer && (state || elementGrayChecked)) {
				setSubtreeChecked((IContainer) element, state, true);
			}
		}
	}

	private void updateParentState(IResource child) {
		if (child == null || child.getParent() == null) {
