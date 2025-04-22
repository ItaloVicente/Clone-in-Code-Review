					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentConnected();
				}
				@Override
				public void handleDocumentFlushed() {
					fireContentChanged();
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentFlushed();
				}
				@Override
				public void handleDocumentDeleted() {
					LocalNonWorkspaceTypedElement.this.update();
					if (sharedDocumentListener != null)
						sharedDocumentListener.handleDocumentDeleted();
