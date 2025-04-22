			menuCreatorListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					switch (event.type) {
					case SWT.Show:
						handleShowProxy((Menu) event.widget);
						break;
					case SWT.Hide:
						handleHideProxy((Menu) event.widget);
						break;
					}
