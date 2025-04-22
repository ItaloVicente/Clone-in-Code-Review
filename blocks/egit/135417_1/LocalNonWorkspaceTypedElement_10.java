				public IEditorInput getDocumentKey(Object element) {
					if (element == LocalNonWorkspaceTypedElement.this) {
						IFileStore store = EFS.getLocalFileSystem().getStore(
								LocalNonWorkspaceTypedElement.this.path);
						if (store != null) {
							return new FileStoreEditorInput(store);
						}
					}
					return super.getDocumentKey(element);
