		defaultItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean doRefresh = minimizedElement.getTags().remove(
						IPresentationEngine.ORIENTATION_HORIZONTAL);
				doRefresh |= minimizedElement.getTags().remove(
						IPresentationEngine.ORIENTATION_VERTICAL);
				if (isShowing && doRefresh) {
					setPaneLocation();
				}
