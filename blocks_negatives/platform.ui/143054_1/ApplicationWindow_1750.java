        }
        return null;
    }

    /**
     * Creates the control for the cool bar manager.
     * <p>
     * Subclasses may override this method to customize the cool bar manager.
     * </p>
     * @param composite the parent used for the control
     *
     * @return an instance of <code>CoolBar</code>
     * @since 3.0
     */
    protected Control createCoolBarControl(Composite composite) {
        if (coolBarManager != null) {
        	if (coolBarManager instanceof ICoolBarManager2) {
