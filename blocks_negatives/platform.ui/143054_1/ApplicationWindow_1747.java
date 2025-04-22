        return new ApplicationWindowLayout();
    }

    /**
     * Returns whether to show a top separator line between the menu bar
     * and the rest of the window contents.  On some platforms such as the Mac,
     * the menu is separated from the main window already, so a separator line
     * is not desired.
     *
     * @return <code>true</code> to show the top separator, <code>false</code>
     *   to not show it
     * @since 3.0
     */
    protected boolean showTopSeperator() {
        return !Util.isMac();
    }

    /**
     * Create the status line if required.
     * @param shell
     */
    protected void createStatusLine(Shell shell) {
        if (statusLineManager != null) {
            statusLineManager.createControl(shell, SWT.NONE);
        }
    }

    /**
     * Returns a new menu manager for the window.
     * <p>
     * Subclasses may override this method to customize the menu manager.
     * </p>
     * @return a menu manager
     */
    protected MenuManager createMenuManager() {
        return new MenuManager();
    }

    /**
     * Returns a new status line manager for the window.
     * <p>
     * Subclasses may override this method to customize the status line manager.
     * </p>
     * @return a status line manager
     */
    protected StatusLineManager createStatusLineManager() {
        return new StatusLineManager();
    }

    /**
     * Returns a new tool bar manager for the window.
     * <p>
     * Subclasses may override this method to customize the tool bar manager.
     * </p>
     * @param style swt style bits used to create the Toolbar
     *
     * @return a tool bar manager
     * @see ToolBarManager#ToolBarManager(int)
     * @see ToolBar for style bits
     */
    protected ToolBarManager createToolBarManager(int style) {
        return new ToolBarManager(style);
    }

    /**
     * Returns a new tool bar manager for the window.
     * <p>
     * By default this method calls <code>createToolBarManager</code>.  Subclasses
     * may override this method to provide an alternative implementation for the
     * tool bar manager.
     * </p>
     *
