		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				QuickAccessEntry entry = (QuickAccessEntry) event.item.getData();
				if (entry != null) {
					switch (event.type) {
					case SWT.MeasureItem:
						entry.measure(event, textLayout, resourceManager, boldStyle);
						break;
					case SWT.PaintItem:
						entry.paint(event, textLayout, resourceManager, boldStyle, grayColor);
						break;
					case SWT.EraseItem:
						entry.erase(event);
						break;
					}
