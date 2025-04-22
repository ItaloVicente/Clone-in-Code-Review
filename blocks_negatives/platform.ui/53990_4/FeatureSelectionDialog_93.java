        listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                getOkButton().setEnabled(!event.getSelection().isEmpty());
            }
        });
