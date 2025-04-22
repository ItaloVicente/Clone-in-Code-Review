		rawTable.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				if (tableLoader != null) {
					TableItem item = (TableItem) event.item;
					int index = rawTable.indexOf(item);
					if (trace)
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.HISTORYVIEW.getLocation(),
								"Item " + index); //$NON-NLS-1$
					tableLoader.loadItem(index);
				}
			}
		});
