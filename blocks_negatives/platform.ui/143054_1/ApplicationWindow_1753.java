     *   this window does not have a cool bar
     * @see #addCoolBar(int)
     * @since 3.2
     */
    public ICoolBarManager getCoolBarManager2() {
        return coolBarManager;
    }

    /**
     * Returns the control for the window's toolbar.
     * <p>
     * Subclasses may override this method to customize the tool bar manager.
     * </p>
     * @return a Control
     */
    protected Control getToolBarControl() {
        if (toolBarManager != null) {
        	if (toolBarManager instanceof IToolBarManager2) {
