	private void createFileAssociations(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Label label = new Label(composite, SWT.NONE);
		label.setFont(composite.getFont());
		label.setText(WorkbenchMessages.ContentTypes_fileAssociationsLabel);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		fileAssociationViewer = new TableViewer(composite);
		fileAssociationViewer.setComparator(new FileSpecComparator());
		fileAssociationViewer.getControl().setFont(composite.getFont());
		fileAssociationViewer.setContentProvider(new FileSpecContentProvider());
		fileAssociationViewer.setLabelProvider(new FileSpecLabelProvider());
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		fileAssociationViewer.getControl().setLayoutData(data);
		fileAssociationViewer.addSelectionChangedListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection.isEmpty()) {
				editButton.setEnabled(false);
				removeButton.setEnabled(false);
				return;
			}
			boolean enabled = true;
			List elements = selection.toList();
			for (Iterator i = elements.iterator(); i.hasNext();) {
				Spec spec = (Spec) i.next();
				if (spec.isPredefined) {
					enabled = false;
