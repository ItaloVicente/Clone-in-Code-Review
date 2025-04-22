		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				outputContext.set(IServiceConstants.ACTIVE_SELECTION,
						event.getSelection());
			}
		});
