		addRootContentTypeButton.addSelectionListener(widgetSelectedAdapter(e -> {
			String id = "userCreated" + System.currentTimeMillis(); //$NON-NLS-1$
			IContentTypeManager manager = (IContentTypeManager) contentTypesViewer.getInput();
			NewContentTypeDialog dialog = new NewContentTypeDialog(ContentTypesPreferencePage.this.getShell(),
					manager, null);
			if (dialog.open() == IDialogConstants.OK_ID) {
				try {
					IContentType newContentType = manager.addContentType(id, dialog.getName(), null);
					contentTypesViewer.refresh();
					contentTypesViewer.setSelection(new StructuredSelection(newContentType));
				} catch (CoreException e1) {
					MessageDialog.openError(getShell(), WorkbenchMessages.ContentTypes_failedAtEditingContentTypes,
							e1.getMessage());
