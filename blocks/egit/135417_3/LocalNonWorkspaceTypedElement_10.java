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

