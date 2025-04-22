    /**
     * Create a new checker that parents the dialog off of parent using the supplied
     * title and message.
     * @param parent the shell used for dialogs
     * @param title the title for dialogs
     * @param message the message for a dialog - this will be prefaced with the name of the resource.
     */
    public ReadOnlyStateChecker(Shell parent, String title, String message) {
        this.shell = parent;
        this.titleMessage = title;
        this.mainMessage = message;
    }
