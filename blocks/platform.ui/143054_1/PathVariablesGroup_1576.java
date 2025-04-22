		initTemporaryState();
	}

	public PathVariablesGroup(boolean multiSelect, int variableType,
			Listener selectionListener) {
		this(multiSelect, variableType);
		this.selectionListener = selectionListener;
	}

	private void addNewVariable() {
		PathVariableDialog dialog = new PathVariableDialog(shell,
				PathVariableDialog.NEW_VARIABLE, variableType,
				pathVariableManager, tempPathVariables.keySet());

		dialog.setResource(currentResource);
		if (dialog.open() == Window.CANCEL) {
