        }
        return null;
    }

    /**
     * Returns the control for the window's cool bar.
     * <p>
     * Subclasses may override this method to customize the cool bar manager.
     * </p>
     *
     * @return an instance of <code>CoolBar</code>
     * @since 3.0
     */
    protected Control getCoolBarControl() {
        if (coolBarManager != null) {
        	if (coolBarManager instanceof ICoolBarManager2) {
