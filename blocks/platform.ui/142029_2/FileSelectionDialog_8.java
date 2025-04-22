		Composite composite = (Composite) super.createDialogArea(parent);

		createMessageArea(composite);

		FileSystemElement input = new FileSystemElement("", null, true);//$NON-NLS-1$
		input.addChild(root);
		root.setParent(input);

		selectionGroup = new CheckboxTreeAndListGroup(composite, input, getFolderProvider(),
				new WorkbenchLabelProvider(), getFileProvider(), new WorkbenchLabelProvider(), SWT.NONE,
				SIZING_SELECTION_WIDGET_WIDTH, // since this page has no other significantly-sized
				SIZING_SELECTION_WIDGET_HEIGHT); // widgets we need to hardcode the combined widget's

		ICheckStateListener listener = event -> getOkButton().setEnabled(selectionGroup.getCheckedElementCount() > 0);

		WorkbenchViewerComparator comparator = new WorkbenchViewerComparator();
		selectionGroup.setTreeComparator(comparator);
		selectionGroup.setListComparator(comparator);
		selectionGroup.addCheckStateListener(listener);

		addSelectionButtons(composite);

		return composite;
	}

	public boolean getExpandAllOnOpen() {
		return expandAllOnOpen;
	}

	private ITreeContentProvider getFileProvider() {
		return new WorkbenchContentProvider() {
			@Override
