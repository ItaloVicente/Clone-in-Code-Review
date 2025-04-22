							if (sharedDocumentListener != null) {
								sharedDocumentListener
										.handleDocumentConnected();
							}
						}

						@Override
						public void handleDocumentFlushed() {
							fireContentChanged();
							if (sharedDocumentListener != null) {
								sharedDocumentListener.handleDocumentFlushed();
							}
						}

						@Override
						public void handleDocumentDeleted() {
							update();
							if (sharedDocumentListener != null) {
								sharedDocumentListener.handleDocumentDeleted();
							}
						}

						@Override
						public void handleDocumentSaved() {
							updateGitState();
							refreshTimestamp();
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

				private IFileStore fileStore;

				private IFileStore getFileStore() {
					IFileStore store = fileStore;
					if (store == null) {
						store = EFS.getLocalFileSystem().getStore(
								LocalNonWorkspaceTypedElement.this.path);
						fileStore = store;
					}
					return store;
