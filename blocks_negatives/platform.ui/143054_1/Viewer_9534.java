    /**
     * Removes the given help listener from this viewer.
     * Has no effect if an identical listener is not registered.
     *
     * @param listener a help listener
     */
    public void removeHelpListener(HelpListener listener) {
        helpListeners.remove(listener);
        if (helpListeners.isEmpty()) {
            Control control = getControl();
            if (control != null && !control.isDisposed()) {
                control.removeHelpListener(this.helpListener);
                helpHooked = false;
            }
        }
    }
