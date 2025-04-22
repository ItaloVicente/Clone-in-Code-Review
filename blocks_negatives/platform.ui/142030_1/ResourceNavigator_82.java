    /**
     * Constructs a new resource navigator view.
     */
    public ResourceNavigator() {
        IDialogSettings viewsSettings = getPlugin().getDialogSettings();

        settings = viewsSettings.getSection(STORE_SECTION);
        if (settings == null) {
            settings = viewsSettings.addNewSection(STORE_SECTION);
        }
