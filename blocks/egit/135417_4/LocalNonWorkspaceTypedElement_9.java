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
