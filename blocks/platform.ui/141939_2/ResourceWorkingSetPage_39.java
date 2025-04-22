		return workingSet;
	}

	private String getWorkingSetName() {
		return text.getText();
	}

	private void handleCheckStateChange(final CheckStateChangedEvent event) {
		BusyIndicator.showWhile(getShell().getDisplay(), () -> {
			IResource resource = (IResource) event.getElement();
			boolean state = event.getChecked();

			tree.setGrayed(resource, false);
			if (resource instanceof IContainer) {
				setSubtreeChecked((IContainer) resource, state, true);
			}
			updateParentState(resource);
			validateInput();
