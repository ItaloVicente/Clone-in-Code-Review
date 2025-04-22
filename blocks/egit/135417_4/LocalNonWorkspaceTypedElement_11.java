				public IEditorInput getDocumentKey(Object element) {
					if (element == LocalNonWorkspaceTypedElement.this) {
						IFileStore store = EFS.getLocalFileSystem().getStore(
								LocalNonWorkspaceTypedElement.this.path);
						if (store != null) {
							return new FileStoreEditorInput(store) {
								@Override
								public <T> T getAdapter(Class<T> adapter) {
									if (adapter == IFile.class
											|| adapter == IResource.class) {
										IResource resource = LocalNonWorkspaceTypedElement.this
												.getResource();
										if (adapter.isInstance(resource)) {
											return adapter.cast(resource);
										}
									}
									return super.getAdapter(adapter);
								}
							};
						}
					}
					return super.getDocumentKey(element);
