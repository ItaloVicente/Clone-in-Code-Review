				public void handleDocumentDisconnected() {
				}
			};
			sharedDocumentAdapter = new EditableSharedDocumentAdapter(
					sharedDocumentAdapterListener) {
				@Override
				public IEditorInput getDocumentKey(Object element) {
					return EditableRevision.this.getDocumentKey(element);
				}
			};
		}
