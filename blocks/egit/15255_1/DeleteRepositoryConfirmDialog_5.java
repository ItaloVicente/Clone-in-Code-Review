
	private static void createIndentedLabel(Composite main, String text) {
		Text widget = UIUtils.createSelectableLabel(main, 0);
		widget.setText(text);
		int indent = 20;
		GridDataFactory.fillDefaults().grab(true, false).indent(indent, 0)
				.applyTo(widget);
	}

	private void setOkButtonEnablement() {
		getButton(IDialogConstants.OK_ID).setEnabled(shouldDeleteGitDir);
	}
