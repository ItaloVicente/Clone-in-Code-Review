							if (sharedDocumentListener != null) {
								sharedDocumentListener.handleDocumentSaved();
							}
						}

						@Override
						public void handleDocumentDisconnected() {
							if (sharedDocumentListener != null) {
								sharedDocumentListener
										.handleDocumentDisconnected();
							}
						}
					}) {

				@Override
				public IEditorInput getDocumentKey(Object element) {
					if (element == LocalNonWorkspaceTypedElement.this) {
						IFileStore store = EFS.getLocalFileSystem().getStore(
								LocalNonWorkspaceTypedElement.this.path);
						if (store != null) {
							return new FakeResourceFileStoreEditorInput(store,
									LocalNonWorkspaceTypedElement.this
											.getResource());
						}
					}
					return super.getDocumentKey(element);
