			boolean checked = checkedStateStore.containsKey(currentElement);
			treeViewer.setChecked(currentElement, checked);
			treeViewer.setGrayed(currentElement, checked
					&& !whiteCheckedTreeItems.contains(currentElement));
		}
	}

	private void createContents(Composite parent, int style, boolean useHeightHint) {
		Composite composite = new Composite(parent, style);
		composite.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createTreeViewer(composite, useHeightHint);
		createListViewer(composite, useHeightHint);

		initialize();
	}

	private void createListViewer(Composite parent, boolean useHeightHint) {
		listViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		if (useHeightHint) {
