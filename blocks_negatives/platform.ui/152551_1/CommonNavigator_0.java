		commonViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				firePropertyChange(PROP_DIRTY);
			}});
