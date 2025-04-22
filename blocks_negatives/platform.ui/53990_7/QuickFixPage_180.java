				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						markersTable.refresh();
						setPageComplete(markersTable.getCheckedElements().length > 0);
					}
