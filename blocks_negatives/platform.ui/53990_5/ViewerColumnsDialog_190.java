		visibleViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleVisibleSelection(event.getSelection());
			}
		});
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleToNonVisibleButton(event);
			}
		});
