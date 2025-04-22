				public void connect(IDocumentProvider provider,
						IEditorInput documentKey) throws CoreException {
					if (documentKey instanceof FakeResourceFileStoreEditorInput) {
						FakeResourceFileStoreEditorInput input = (FakeResourceFileStoreEditorInput) documentKey;
						try {
							input.setResource(null);
							super.connect(provider, input);
						} finally {
							input.setResource(LocalNonWorkspaceTypedElement.this
									.getResource());
						}
					} else {
						super.connect(provider, documentKey);
					}
