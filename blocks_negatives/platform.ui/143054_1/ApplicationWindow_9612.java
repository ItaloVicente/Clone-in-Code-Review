        }
        return null;
    }

    /**
     * Returns the default font used for this window.
     * <p>
     * The default implementation of this framework method
     * obtains the symbolic name of the font from the
     * <code>getSymbolicFontName</code> framework method
     * and retrieves this font from JFace's font
     * registry using <code>JFaceResources.getFont</code>.
     * Subclasses may override to use a different registry,
     * etc.
     * </p>
     *
     * @return the default font, or <code>null</code> if none
     */
    protected Font getFont() {
        return JFaceResources.getFont(getSymbolicFontName());
    }

    /**
     * Returns the menu bar manager for this window (if it has one).
     *
     * @return the menu bar manager, or <code>null</code> if
     *   this window does not have a menu bar
     * @see #addMenuBar()
     */
    public MenuManager getMenuBarManager() {
        return menuBarManager;
    }

    /**
     * Returns the status line manager for this window (if it has one).
     *
     * @return the status line manager, or <code>null</code> if
     *   this window does not have a status line
     * @see #addStatusLine
     */
    protected StatusLineManager getStatusLineManager() {
        return statusLineManager;
    }

    /**
     * Returns the symbolic font name of the font to be
     * used to display text in this window.
     * This is not recommended and is included for backwards
     * compatability.
     * It is recommended to use the default font provided by
     * SWT (that is, do not set the font).
     *
     * @return the symbolic font name
     */
    public String getSymbolicFontName() {
        return JFaceResources.TEXT_FONT;
    }

    /**
     * Returns the tool bar manager for this window (if it has one).
     *
     * @return the tool bar manager, or <code>null</code> if
     *   this window does not have a tool bar
     * @see #addToolBar(int)
     */
    public ToolBarManager getToolBarManager() {
    	if (toolBarManager instanceof ToolBarManager) {
