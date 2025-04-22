     */
    public IToolBarManager getToolBarManager2() {
        return toolBarManager;
    }

    /**
     * Returns the cool bar manager for this window.
     *
     * @return the cool bar manager, or <code>null</code> if
     *   this window does not have a cool bar
     * @see #addCoolBar(int)
     * @since 3.0
     */
    public CoolBarManager getCoolBarManager() {
    	if (coolBarManager instanceof CoolBarManager) {
