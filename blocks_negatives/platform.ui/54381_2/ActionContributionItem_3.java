			buttonListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					switch (event.type) {
					case SWT.Dispose:
						handleWidgetDispose(event);
						break;
					case SWT.Selection:
						Widget ew = event.widget;
						if (ew != null) {
							handleWidgetSelection(event, ((Button) ew)
									.getSelection());
						}
						break;
