    /**
     * The parent contribution manager.
     */
    private IContributionManager parentMgr;

    /**
     * Maps each item in the manager to a wrapper.  The wrapper is used to
     * control the visibility of each item.
     */
    private Map<IContributionItem, SubContributionItem> mapItemToWrapper = new HashMap<>();

    /**
     * The visibility of the manager,
     */
    private boolean visible = false;

    /**
     * Constructs a new <code>SubContributionManager</code>
     *
     * @param mgr the parent contribution manager.  All contributions made to the
     *      <code>SubContributionManager</code> are forwarded and appear in the
     *      parent manager.
     */
    public SubContributionManager(IContributionManager mgr) {
        super();
        parentMgr = mgr;
    }

    @Override
