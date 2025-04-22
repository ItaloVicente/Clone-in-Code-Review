    private IAdaptable pageInput;

    private IMenuManager parentMenuManager;

    private boolean replaceEnabled = true;

    private static String PAGE_PROBLEMS_TITLE = WorkbenchMessages.OpenPerspectiveMenu_pageProblemsTitle;

    private static String PAGE_PROBLEMS_MESSAGE = WorkbenchMessages.OpenPerspectiveMenu_errorUnknownInput;

    /**
     * Constructs a new menu.
     */
    public OpenPerspectiveMenu(IMenuManager menuManager, IWorkbenchWindow window) {
        this(window);
        this.parentMenuManager = menuManager;
    }

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
    public OpenPerspectiveMenu(IWorkbenchWindow window) {
        this(window, null);
        showActive(true);
    }

    /**
     * Constructs a new instance of <code>OpenNewPageMenu</code>.
     *
     * @param window the window where a new page is created if an item within
     *		the menu is selected
     * @param input the page input
     */
    public OpenPerspectiveMenu(IWorkbenchWindow window, IAdaptable input) {
        super(window, "Open New Page Menu");//$NON-NLS-1$
        this.pageInput = input;
    }

    /**
     * Return whether or not the menu can be run. Answer true unless the current mode
     * is replace and the replaceEnabled flag is false.
     */
    private boolean canRun() {
        if (openPerspectiveSetting().equals(
                IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE)) {
