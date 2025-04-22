    /**
     * Make ole active so that the controls are rendered.
     */
    private void oleActivate() {
        if (clientSite == null || clientFrame == null
                || clientFrame.isDisposed())
            return;
