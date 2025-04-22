    /**
     * A constant used by <code>setMinimumItemsToShow</code> and <code>getMinimumItemsToShow</code>
     * to indicate that all tool items should be shown in the cool item.
     */
    public static final int SHOW_ALL_ITEMS = -1;

    /**
     * The pull down menu used to list all hidden tool items if the current
     * size is less than the preffered size.
     */
    private MenuManager chevronMenuManager = null;

    /**
     * The widget created for this item; <code>null</code> before creation
     * and after disposal.
     */
    private CoolItem coolItem = null;

    /**
     * Current height of cool item
     */
    private int currentHeight = -1;

    /**
     * Current width of cool item.
     */
    private int currentWidth = -1;

    /**
     * A flag indicating that this item has been disposed. This prevents future
     * method invocations from doing things they shouldn't.
     */
    private boolean disposed = false;

    /**
     * Mininum number of tool items to show in the cool item widget.
     */
    private int minimumItemsToShow = SHOW_ALL_ITEMS;

    /**
     * The tool bar manager used to manage the tool items contained in the cool
     * item widget.
     */
    private ToolBarManager toolBarManager = null;

    /**
     * Enable/disable chevron support.
     */
    private boolean useChevron = true;

    /**
     * Convenience method equivalent to <code>ToolBarContributionItem(new ToolBarManager(), null)</code>.
     */
    public ToolBarContributionItem() {
        this(new ToolBarManager(), null);
    }

    /**
     * Convenience method equivalent to <code>ToolBarContributionItem(toolBarManager, null)</code>.
     *
     * @param toolBarManager
     *            the tool bar manager
     */
    public ToolBarContributionItem(IToolBarManager toolBarManager) {
        this(toolBarManager, null);
    }

    /**
     * Creates a tool bar contribution item.
     *
     * @param toolBarManager
     *            the tool bar manager to wrap
     * @param id
     *            the contribution item id, or <code>null</code> if none
     */
    public ToolBarContributionItem(IToolBarManager toolBarManager, String id) {
        super(id);
        Assert.isTrue(toolBarManager instanceof ToolBarManager);
        this.toolBarManager = (ToolBarManager) toolBarManager;
    }

    /**
     * Checks whether this contribution item has been disposed. If it has, and
     * the tracing options are active, then it prints some debugging
     * information.
     *
     * @return <code>true</code> if the item is disposed; <code>false</code>
     *         otherwise.
     *
     */
    private final boolean checkDisposed() {
        if (disposed) {
            if (Policy.TRACE_TOOLBAR) {
                System.out
                new Exception().printStackTrace(System.out);
            }

            return true;
        }

        return false;
    }

    @Override
