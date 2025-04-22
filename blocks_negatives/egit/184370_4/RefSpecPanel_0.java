	private void createDeleteCreationPanel() {
		final Group deletePanel = new Group(panel, SWT.NONE);
		deletePanel.setText(UIText.RefSpecPanel_deletionGroup);
		deletePanel
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
