		this.value = value;
		updateContents(value);
	}

	protected Label getDefaultLabel() {
		return defaultLabel;
	}

	protected abstract Object openDialogBox(Control cellEditorWindow);

	protected void updateContents(Object value) {
		if (defaultLabel == null) {
