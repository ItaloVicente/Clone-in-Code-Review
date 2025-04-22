        super.configureShell(shell);

        createTrimWidgets(shell);
    }

    /**
     * Creates the trim widgets around the content area.
     *
     * @param shell the shell
     * @since 3.0
     */
    protected void createTrimWidgets(Shell shell) {
        if (menuBarManager != null) {
        	boolean resizeHasOccurredBackup = this.resizeHasOccurred;
        	shell.setMenuBar(menuBarManager.createMenuBar((Decorations) shell));
        	this.resizeHasOccurred = resizeHasOccurredBackup;
            menuBarManager.updateAll(true);
        }

        if (showTopSeperator()) {
