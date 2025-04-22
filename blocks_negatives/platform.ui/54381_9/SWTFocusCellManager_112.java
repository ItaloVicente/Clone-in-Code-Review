		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if( event.selection.isEmpty() ) {
					setFocusCell(null);
				}
