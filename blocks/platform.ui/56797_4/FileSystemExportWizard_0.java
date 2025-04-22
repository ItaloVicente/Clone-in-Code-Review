				if (currentEditor != null) {
					Object selectedResource = Adapters.getAdapter(currentEditor.getEditorInput(), IResource.class,
							true);
					if (selectedResource != null) {
						selection = new StructuredSelection(selectedResource);
					}
				}
