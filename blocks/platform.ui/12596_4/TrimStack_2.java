	private void createEmtpyEditorAreaMenu() {
		MenuItem restoreItem = new MenuItem(trimStackMenu, SWT.NONE);
		restoreItem.setText(Messages.TrimStack_RestoreText);
		restoreItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED);
			}
		});
	}

	private void createPartMenu(final MPart selectedPart) {
		MenuItem orientationItem = new MenuItem(trimStackMenu, SWT.CASCADE);
		orientationItem.setText(Messages.TrimStack_OrientationMenu);
		Menu orientationMenu = new Menu(orientationItem);
		orientationItem.setMenu(orientationMenu);

		MenuItem defaultItem = new MenuItem(orientationMenu, SWT.RADIO);
		defaultItem.setText(Messages.TrimStack_DefaultOrientationItem);
		defaultItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				boolean doRefresh = minimizedElement.getTags().remove(
						IPresentationEngine.ORIENTATION_HORIZONTAL);
				doRefresh |= minimizedElement.getTags().remove(
						IPresentationEngine.ORIENTATION_VERTICAL);
				if (isShowing && doRefresh) {
					setPaneLocation(hostPane);
