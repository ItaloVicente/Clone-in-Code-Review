			removeButton = new Button(buttonArea, SWT.PUSH);
			removeButton.setEnabled(false);
			removeButton
					.setText(WorkbenchMessages.ContentTypes_fileAssociationsRemoveLabel);
			setButtonLayoutData(removeButton);
			removeButton.addSelectionListener(widgetSelectedAdapter(event -> {
				IContentType contentType = getSelectedContentType();
				Spec[] specs = getSelectedSpecs();
				MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID,
						0, new IStatus[0],
						WorkbenchMessages.ContentTypes_errorDialogMessage,
						null);
				for (Spec spec : specs) {
					try {
						if (spec.name != null) {
							contentType.removeFileSpec(spec.name,
									IContentType.FILE_NAME_SPEC);
						} else if (spec.ext != null) {
							contentType.removeFileSpec(spec.ext,
									IContentType.FILE_EXTENSION_SPEC);
						}
					} catch (CoreException e) {
						result.add(e.getStatus());
