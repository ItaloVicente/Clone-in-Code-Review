		MenuItem verticalItem = new MenuItem(orientationMenu, SWT.RADIO);
		verticalItem.setText(Messages.TrimStack_Vertical);
		verticalItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!minimizedElement.getTags().contains(IPresentationEngine.ORIENTATION_VERTICAL)) {
					minimizedElement.getTags().remove(IPresentationEngine.ORIENTATION_HORIZONTAL);
					minimizedElement.getTags().add(IPresentationEngine.ORIENTATION_VERTICAL);
					if (isShowing) {
						setPaneLocation(hostPane);
					}
				}
