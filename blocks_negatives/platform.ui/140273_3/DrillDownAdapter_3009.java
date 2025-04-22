    /**
     * Resets the drill down adapter.
     * <p>
     * This method is typically called when the input for the underlying view
     * is reset by something other than the adapter.
     * On return the drill stack has been cleared and the navigation buttons
     * reflect the new state of the underlying viewer.
     * </p>
     */
    public void reset() {
        fDrillStack.reset();
        updateNavigationButtons();
    }
