
	private synchronized ISharedDocumentAdapter getSharedDocumentAdapter() {
		if (sharedDocumentAdapter == null)
			sharedDocumentAdapter = new EditableSharedDocumentAdapter(new EditableSharedDocumentAdapter.ISharedDocumentAdapterListener() {
				public void handleDocumentConnected() {
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentConnected();
				}
				public void handleDocumentFlushed() {
					fireContentChanged();
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentFlushed();
				}
				public void handleDocumentDeleted() {
					LocalNonWorkspaceTypedElement.this.update();
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentDeleted();
				}
				public void handleDocumentSaved() {
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentSaved();
				}
				public void handleDocumentDisconnected() {
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentDisconnected();
				}
			});
		return sharedDocumentAdapter;
	}

