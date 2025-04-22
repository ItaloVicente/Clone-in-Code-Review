		MenuItem horizontalItem = new MenuItem(orientationMenu, SWT.RADIO);
		horizontalItem.setText(Messages.TrimStack_Horizontal);
		horizontalItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!minimizedElement.getTags()
						.contains(IPresentationEngine.ORIENTATION_HORIZONTAL)) {
					minimizedElement.getTags().remove(IPresentationEngine.ORIENTATION_VERTICAL);
					minimizedElement.getTags().add(IPresentationEngine.ORIENTATION_HORIZONTAL);
					if (isShowing) {
						setPaneLocation(hostPane);
