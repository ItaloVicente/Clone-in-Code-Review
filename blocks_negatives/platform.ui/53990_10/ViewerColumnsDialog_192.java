		nonVisibleViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleNonVisibleSelection(event.getSelection());
			}
		});
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleToVisibleButton(event);
			}
		});
