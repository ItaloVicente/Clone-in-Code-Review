		final Listener resizeListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				CTabFolder folder = (CTabFolder) event.widget;
				for (Control child : folder.getChildren()) {
					if (isReskinRequired(child)) {
						child.reskin(SWT.NONE);
					}
