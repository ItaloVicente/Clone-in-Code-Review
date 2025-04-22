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
