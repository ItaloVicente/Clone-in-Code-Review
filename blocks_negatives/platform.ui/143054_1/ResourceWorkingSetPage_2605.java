        return workingSet;
    }

    /**
     * Returns the name entered in the working set name field.
     *
     * @return the name entered in the working set name field.
     */
    private String getWorkingSetName() {
        return text.getText();
    }

    /**
     * Called when the checked state of a tree item changes.
     *
     * @param event the checked state change event.
     */
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
