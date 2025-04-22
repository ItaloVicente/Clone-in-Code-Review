	protected ISharedDocumentAdapter createSharedDocumentAdapter() {
		return new SharedDocumentAdapter() {

			@Override
			public IEditorInput getDocumentKey(Object element) {
				return StorageTypedElement.this.getDocumentKey(element);
			}

			@Override
			public void flushDocument(IDocumentProvider provider,
					IEditorInput documentKey, IDocument document,
					boolean overwrite) throws CoreException {
			}
		};
	}

