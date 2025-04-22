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
					}
				} catch (CoreException ex) {
					StatusUtil.handleStatus(ex.getStatus(), StatusManager.SHOW, shell);
					WorkbenchPlugin.log(ex);
				} finally {
					fileAssociationViewer.refresh(false);
