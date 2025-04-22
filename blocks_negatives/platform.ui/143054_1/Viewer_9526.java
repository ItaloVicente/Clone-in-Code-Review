    /**
     * Adds a listener for help requests in this viewer.
     * Has no effect if an identical listener is already registered.
     *
     * @param listener a help listener
     */
    public void addHelpListener(HelpListener listener) {
        helpListeners.add(listener);
        if (!helpHooked) {
            Control control = getControl();
            if (control != null && !control.isDisposed()) {
                if (this.helpListener == null) {
