    /**
     * The identifier for this contribution item, of <code>null</code> if none.
     */
    private String id = null;

    /**
     * Indicates this item is visible in its manager; <code>true</code>
     * by default.
     */
    private boolean visible = true;

    /**
     * The parent contribution manager for this item
     */
    private IContributionManager parent;

    /**
     * Creates a contribution item with a <code>null</code> id.
     * Calls <code>this(String)</code> with <code>null</code>.
     */
    protected ContributionItem() {
        this(null);
    }

    /**
     * Creates a contribution item with the given (optional) id.
     * The given id is used to find items in a contribution manager,
     * and for positioning items relative to other items.
     *
     * @param id the contribution item identifier, or <code>null</code>
     */
    protected ContributionItem(String id) {
        this.id = id;
    }

    /**
     * The default implementation of this <code>IContributionItem</code>
     * method does nothing. Subclasses may override.
     */
    @Override
