			String varName = (String) selectedItem.getData();
			if (isBuiltInVariable(varName))
				return false;
		}
		return true;
	}

	private boolean isBuiltInVariable(String varName) {
		if (currentResource != null) {
			return !pathVariableManager.isUserDefined(varName);
		}
		return false;
	}

	private GridData setButtonLayoutData(Button button) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics,
				IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		return data;
	}

	public void setEnabled(boolean enabled) {
		if (variableTable != null && !variableTable.getTable().isDisposed()) {
			variableLabel.setEnabled(enabled);
			variableTable.getTable().setEnabled(enabled);
			addButton.setEnabled(enabled);
			if (enabled) {
