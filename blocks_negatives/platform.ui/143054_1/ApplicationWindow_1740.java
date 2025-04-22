    /**
     * Menu bar manager, or <code>null</code> if none (default).
     *
     * @see #addMenuBar
     */
    private MenuManager menuBarManager = null;

    /**
     * Tool bar manager, or <code>null</code> if none (default).
     *
     * @see #addToolBar
     */
    private IToolBarManager toolBarManager = null;

    /**
     * Status line manager, or <code>null</code> if none (default).
     *
     * @see #addStatusLine
     */
    private StatusLineManager statusLineManager = null;

    /**
     * Cool bar manager, or <code>null</code> if none (default).
     *
     * @see #addCoolBar
     * @since 3.0
     */
    private ICoolBarManager coolBarManager = null;

    /**
     * The seperator between the menu bar and the rest of the window.
     */
    protected Label seperator1;

    /**
     * A flag indicating that an operation is running.
     */
    private boolean operationInProgress = false;

    /**
     * Internal application window layout class.
     * This vertical layout supports a tool bar area (fixed size),
     * a separator line, the content area (variable size), and a
     * status line (fixed size).
     */
    /*package*/class ApplicationWindowLayout extends Layout {

        static final int VGAP = 2;

        static final int BAR_SIZE = 23;

        @Override
