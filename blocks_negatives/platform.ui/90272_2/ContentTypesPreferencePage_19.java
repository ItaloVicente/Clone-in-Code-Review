			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Shell shell = composite.getShell();
					IContentType selectedContentType = getSelectedContentType();
					FileExtensionDialog dialog = new FileExtensionDialog(
							shell,
							WorkbenchMessages.ContentTypes_addDialog_title,
							IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
							WorkbenchMessages.ContentTypes_addDialog_messageHeader,
							WorkbenchMessages.ContentTypes_addDialog_message,
							WorkbenchMessages.ContentTypes_addDialog_label);
					if (dialog.open() == Window.OK) {
						String name = dialog.getName();
						String extension = dialog.getExtension();
						try {
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
