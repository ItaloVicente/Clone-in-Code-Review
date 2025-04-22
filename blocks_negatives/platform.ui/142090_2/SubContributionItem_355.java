    /**
     * The visibility of the item.
     */
    private boolean visible;

    /**
     * The inner item for this contribution.
     */
    private IContributionItem innerItem;

    /**
     * Creates a new <code>SubContributionItem</code>.
     * @param item the contribution item to be wrapped
     */
    public SubContributionItem(IContributionItem item) {
        innerItem = item;
    }

    /**
     * The default implementation of this <code>IContributionItem</code>
     * delegates to the inner item. Subclasses may override.
     */
    @Override
