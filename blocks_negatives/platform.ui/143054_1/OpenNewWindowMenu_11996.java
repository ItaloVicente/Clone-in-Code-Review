    /**
     * Constructs a new instance of <code>OpenNewPageMenu</code>.
     *
     * @param window the window where a new page is created if an item within
     *		the menu is selected
     * @param input the page input
     */
    public OpenNewWindowMenu(IWorkbenchWindow window, IAdaptable input) {
        super(window, "Open New Page Menu");//$NON-NLS-1$
        this.pageInput = input;
    }
