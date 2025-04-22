		v.getTable().addListener(SWT.EraseItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				event.detail &= ~SWT.SELECTED;
			}
		});
