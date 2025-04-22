     *
     * @return a tool bar manager
     * @since 3.2
     * @see #createToolBarManager(int)
     */
    protected IToolBarManager createToolBarManager2(int style) {
        return createToolBarManager(style);
    }

    /**
     * Returns a new cool bar manager for the window.
     * <p>
     * Subclasses may override this method to customize the cool bar manager.
     * </p>
     *
     * @param style swt style bits used to create the Coolbar
     *
     * @return a cool bar manager
     * @since 3.0
     * @see CoolBarManager#CoolBarManager(int)
     * @see CoolBar for style bits
     */
    protected CoolBarManager createCoolBarManager(int style) {
        return new CoolBarManager(style);
    }

    /**
     * Returns a new cool bar manager for the window.
     * <p>
     * By default this method calls <code>createCoolBarManager</code>.  Subclasses
     * may override this method to provide an alternative implementation for the
     * cool bar manager.
     * </p>
     *
