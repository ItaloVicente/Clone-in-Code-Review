	private void editSelectedVariable() {
		TableItem item = variableTable.getTable().getItem(variableTable.getTable()
				.getSelectionIndex());
		String variableName = (String) item.getData();
		IPath variableValue = tempPathVariables.get(variableName);

		PathVariableDialog dialog = new PathVariableDialog(shell,
				PathVariableDialog.EXISTING_VARIABLE, variableType,
				pathVariableManager, tempPathVariables.keySet());
		dialog.setVariableName(variableName);
		dialog.setVariableValue(variableValue.toOSString());
		dialog.setResource(currentResource);

		if (dialog.open() == Window.CANCEL) {
