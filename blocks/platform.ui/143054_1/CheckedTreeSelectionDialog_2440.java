		Composite composite = (Composite) super.createDialogArea(parent);
		Label messageLabel = createMessageArea(composite);
		CheckboxTreeViewer treeViewer = createTreeViewer(composite);
		Control buttonComposite = createSelectionButtons(composite);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);
		Tree treeWidget = treeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
		if (fIsEmpty) {
			messageLabel.setEnabled(false);
			treeWidget.setEnabled(false);
			buttonComposite.setEnabled(false);
		}
		return composite;
	}

	protected CheckboxTreeViewer createTreeViewer(Composite parent) {
		if (fContainerMode) {
