		horizontalItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!minimizedElement.getTags()
						.contains(IPresentationEngine.ORIENTATION_HORIZONTAL)) {
					minimizedElement.getTags().remove(IPresentationEngine.ORIENTATION_VERTICAL);
					minimizedElement.getTags().add(IPresentationEngine.ORIENTATION_HORIZONTAL);
					if (isShowing) {
						setPaneLocation();
					}
