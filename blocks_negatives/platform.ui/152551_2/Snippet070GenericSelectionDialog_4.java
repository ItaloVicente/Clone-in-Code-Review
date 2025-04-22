			listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					getButton(IDialogConstants.OK_ID).setEnabled(!event.getSelection().isEmpty());
				}
			});
