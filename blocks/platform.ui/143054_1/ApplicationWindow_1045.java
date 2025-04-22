		super.configureShell(shell);

		createTrimWidgets(shell);
	}

	protected void createTrimWidgets(Shell shell) {
		if (menuBarManager != null) {
			boolean resizeHasOccurredBackup = this.resizeHasOccurred;
			shell.setMenuBar(menuBarManager.createMenuBar((Decorations) shell));
			this.resizeHasOccurred = resizeHasOccurredBackup;
			menuBarManager.updateAll(true);
		}

		if (showTopSeperator()) {
