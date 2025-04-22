			menuItemListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					switch (event.type) {
					case SWT.Dispose:
						handleWidgetDispose(event);
						break;
					case SWT.DefaultSelection:
					case SWT.Selection:
						if (event.widget != null) {
							handleWidgetSelection(event);
						}
						break;
					case SWT.Help:
						handleHelpRequest();
						break;
