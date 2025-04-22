        return removeAllWhenShown;
    }

    /**
     * Notifies all listeners that this menu is about to appear.
     */
    private void handleAboutToShow() {
        if (removeAllWhenShown) {
