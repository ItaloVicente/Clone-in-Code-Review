		table.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				setDataCalled = true;
				TableItem item = (TableItem) event.item;
				visibleItems.add(item);
			}
