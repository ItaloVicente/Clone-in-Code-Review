
	private static class LocalResourceSaver
			implements ISharedDocumentAdapterListener {

		LocalResourceTypedElement element;

		public LocalResourceSaver(LocalResourceTypedElement element) {
			this.element = element;
		}

		@Override
		public void handleDocumentConnected() {
		}

		@Override
		public void handleDocumentDisconnected() {
		}

		@Override
		public void handleDocumentFlushed() {
			try {
				element.saveDocument(true, null);
			} catch (CoreException e) {
				Activator.handleStatus(e.getStatus(), true);
			}
		}

		@Override
		public void handleDocumentDeleted() {
		}

		@Override
		public void handleDocumentSaved() {
		}

	}
