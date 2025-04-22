	private String removeParentVariable(String value) {
		return pathVariableManager.convertToUserEditableFormat(value, false);
	}

	public boolean performOk() {
		try {
