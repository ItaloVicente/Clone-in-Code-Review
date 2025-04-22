			removeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					IContentType contentType = getSelectedContentType();
					Spec[] specs = getSelectedSpecs();
					MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID,
							0, new IStatus[0],
							WorkbenchMessages.ContentTypes_errorDialogMessage,
							null);
					for (int i = 0; i < specs.length; i++) {
						Spec spec = specs[i];
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
