    /**
     * Updates the enabled state for each navigation button.
     */
    protected void updateNavigationButtons() {
        if (homeAction != null) {
            homeAction.setEnabled(canGoHome());
            backAction.setEnabled(canGoBack());
            forwardAction.setEnabled(canGoInto());
        }
    }
