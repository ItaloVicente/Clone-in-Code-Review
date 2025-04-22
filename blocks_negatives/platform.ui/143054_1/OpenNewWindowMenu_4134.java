    /**
     * Constructs a new instance of <code>OpenNewPageMenu</code>.
     * <p>
     * If this method is used be sure to set the page input by invoking
     * <code>setPageInput</code>.  The page input is required when the user
     * selects an item in the menu.  At that point the menu will attempt to
     * open a new page with the selected perspective and page input.  If there
     * is no page input an error dialog will be opened.
     * </p>
     *
     * @param window the window where a new page is created if an item within
     *		the menu is selected
     */
    public OpenNewWindowMenu(IWorkbenchWindow window) {
        this(window, null);
    }
