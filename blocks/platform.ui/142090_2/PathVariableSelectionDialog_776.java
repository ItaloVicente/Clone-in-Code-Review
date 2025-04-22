	}

	private void updateExtendButtonState() {
		PathVariablesGroup.PathVariableElement[] selection = pathVariablesGroup
				.getSelection();
		Button extendButton = getButton(EXTEND_ID);

		if (extendButton == null) {
