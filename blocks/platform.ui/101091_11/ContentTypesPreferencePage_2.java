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
				}
			}
			editButton.setEnabled(enabled && selection.size() == 1);
			removeButton.setEnabled(enabled);
		});
		Composite buttonArea = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		buttonArea.setLayout(layout);
		data = new GridData(SWT.DEFAULT, SWT.TOP, false, false);
		buttonArea.setLayoutData(data);

		addButton = new Button(buttonArea, SWT.PUSH);
		addButton.setFont(composite.getFont());
		addButton.setText(WorkbenchMessages.ContentTypes_fileAssociationsAddLabel);
		addButton.setEnabled(false);
		setButtonLayoutData(addButton);
		addButton.addSelectionListener(widgetSelectedAdapter(e -> {
			Shell shell = composite.getShell();
			IContentType selectedContentType = getSelectedContentType();
			FileExtensionDialog dialog = new FileExtensionDialog(shell, WorkbenchMessages.ContentTypes_addDialog_title,
					IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
					WorkbenchMessages.ContentTypes_addDialog_messageHeader,
					WorkbenchMessages.ContentTypes_addDialog_message, WorkbenchMessages.ContentTypes_addDialog_label);
			if (dialog.open() == Window.OK) {
				String name = dialog.getName();
				String extension = dialog.getExtension();
				try {
					if (name.equals("*")) { //$NON-NLS-1$
						selectedContentType.addFileSpec(extension, IContentType.FILE_EXTENSION_SPEC);
					} else {
						selectedContentType.addFileSpec(name + (extension.length() > 0 ? ('.' + extension) : ""), //$NON-NLS-1$
								IContentType.FILE_NAME_SPEC);
