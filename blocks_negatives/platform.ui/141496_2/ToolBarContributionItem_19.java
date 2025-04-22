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
