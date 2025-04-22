		removeContentTypeButton.addSelectionListener(widgetSelectedAdapter(e -> {
			IContentType selectedContentType = getSelectedContentType();
			try {
				Platform.getContentTypeManager().removeContentType(selectedContentType.getId());
				contentTypesViewer.refresh();
			} catch (CoreException e1) {
				MessageDialog.openError(getShell(), WorkbenchMessages.ContentTypes_failedAtEditingContentTypes,
						e1.getMessage());
