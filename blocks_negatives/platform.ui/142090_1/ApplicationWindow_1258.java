     *
     * @return a cool bar manager
     * @since 3.2
     * @see #createCoolBarManager(int)
     */
    protected ICoolBarManager createCoolBarManager2(int style) {
        return createCoolBarManager(style);
    }

    /**
     * Creates the control for the tool bar manager.
     * <p>
     * Subclasses may override this method to customize the tool bar manager.
     * </p>
     * @param parent the parent used for the control
     * @return a Control
     */
    protected Control createToolBarControl(Composite parent) {
        if (toolBarManager != null) {
        	if (toolBarManager instanceof IToolBarManager2) {
