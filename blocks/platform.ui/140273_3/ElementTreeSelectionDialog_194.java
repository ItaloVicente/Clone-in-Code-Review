		Composite composite = (Composite) super.createDialogArea(parent);

		Label messageLabel = createMessageArea(composite);
		TreeViewer treeViewer = createTreeViewer(composite);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);

		Tree treeWidget = treeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());

		if (fIsEmpty) {
			messageLabel.setEnabled(false);
			treeWidget.setEnabled(false);
		}

		return composite;
	}

	protected TreeViewer createTreeViewer(Composite parent) {
		int style = SWT.BORDER | (fAllowMultiple ? SWT.MULTI : SWT.SINGLE);

		fViewer = doCreateTreeViewer(parent, style);
		fViewer.setContentProvider(fContentProvider);
		fViewer.setLabelProvider(fLabelProvider);
		fViewer.addSelectionChangedListener(event -> {
