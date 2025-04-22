				public IEditorInput getDocumentKey(Object element) {
					if (element == LocalNonWorkspaceTypedElement.this) {
						IFileStore store = getFileStore();
						if (store != null) {
							return new FakeResourceFileStoreEditorInput(store,
									LocalNonWorkspaceTypedElement.this
											.getResource());
						}
					}
					return super.getDocumentKey(element);
