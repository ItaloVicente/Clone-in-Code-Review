			editButton = new Button(buttonArea, SWT.PUSH);
			editButton.setFont(composite.getFont());
			editButton
					.setText(WorkbenchMessages.ContentTypes_fileAssociationsEditLabel);
			editButton.setEnabled(false);
			setButtonLayoutData(editButton);
			editButton.addSelectionListener(widgetSelectedAdapter(e -> {
				Shell shell = composite.getShell();
				IContentType selectedContentType = getSelectedContentType();
				Spec spec = getSelectedSpecs()[0];
				FileExtensionDialog dialog = new FileExtensionDialog(
						shell,
						WorkbenchMessages.ContentTypes_editDialog_title,
						IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
						WorkbenchMessages.ContentTypes_editDialog_messageHeader,
						WorkbenchMessages.ContentTypes_editDialog_message,
						WorkbenchMessages.ContentTypes_editDialog_label);
				if (spec.name == null) {
				} else {
					dialog.setInitialValue(spec.name);
				}
				if (dialog.open() == Window.OK) {
					String name = dialog.getName();
					String extension = dialog.getExtension();
					try {
						if (spec.name != null) {
							selectedContentType.removeFileSpec(spec.name,
									IContentType.FILE_NAME_SPEC);
						} else if (spec.ext != null) {
							selectedContentType.removeFileSpec(spec.ext,
									IContentType.FILE_EXTENSION_SPEC);
						}

							selectedContentType.addFileSpec(extension,
									IContentType.FILE_EXTENSION_SPEC);
						} else {
							selectedContentType
									.addFileSpec(
											name
													+ (extension.length() > 0 ? ('.' + extension)
															: ""), //$NON-NLS-1$
											IContentType.FILE_NAME_SPEC);
						}
					} catch (CoreException ex) {
						StatusUtil.handleStatus(ex.getStatus(),
								StatusManager.SHOW, shell);
						WorkbenchPlugin.log(ex);
					} finally {
						fileAssociationViewer.refresh(false);
