		final Listener passThrough = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!event.widget.isDisposed()) {
					Widget realItem = (Widget) event.widget.getData();
					if (!realItem.isDisposed()) {
						int style = event.widget.getStyle();
						if (event.type == SWT.Selection
								&& ((style & (SWT.TOGGLE | SWT.CHECK | SWT.RADIO)) != 0)
								&& realItem instanceof MenuItem) {
							((MenuItem) realItem)
									.setSelection(((MenuItem) event.widget)
											.getSelection());
						}
						event.widget = realItem;
						realItem.notifyListeners(event.type, event);
