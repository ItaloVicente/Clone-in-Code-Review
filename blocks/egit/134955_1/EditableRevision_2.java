
	private static class EditableRevisionSharedDocumentAdapter
			extends EditableSharedDocumentAdapter {

		private final EditableRevision editable;

		public EditableRevisionSharedDocumentAdapter(
				EditableRevision editable) {
			super(new ISharedDocumentAdapterListener() {

				@Override
				public void handleDocumentConnected() {
				}

				@Override
				public void handleDocumentDisconnected() {
				}

				@Override
				public void handleDocumentFlushed() {
				}

				@Override
				public void handleDocumentDeleted() {
				}

				@Override
				public void handleDocumentSaved() {
				}
			});
			this.editable = editable;
		}

		@Override
		public void flushDocument(IDocumentProvider provider,
				IEditorInput documentKey, IDocument document, boolean overwrite)
				throws CoreException {
			super.flushDocument(provider, documentKey, document, overwrite);
			if (document != null && editable.input != null) {
				try {
					editable.setContent(
							document.get().getBytes(editable.getCharset()));
					saveDocument(documentKey, overwrite, null);
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(
							Activator.createErrorStatus(
									MessageFormat.format(
											UIText.EditableRevision_CannotSave,
											editable.getName(),
											editable.getContentIdentifier()),
									e));
				}
			}
		}

		@Override
		public IEditorInput getDocumentKey(Object element) {
			return editable.getDocumentKey(element);
		}
	}

