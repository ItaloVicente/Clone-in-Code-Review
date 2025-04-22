    /**
     * The identifier for the replaced contribution item.
     */
    private final String id;

    /**
     * The height of the SWT widget corresponding to the replaced contribution
     * item.
     */
    private final int storedHeight;

    /**
     * The minimum number of items to display on the replaced contribution
     * item.
     */
    private final int storedMinimumItems;

    /**
     * Whether the replaced contribution item would display chevrons.
     */
    private final boolean storedUseChevron;

    /**
     * The width of the SWT widget corresponding to the replaced contribution
     * item.
     */
    private final int storedWidth;

    /**
     * Constructs a new instance of <code>PlaceholderContributionItem</code>
     * from the item it is intended to replace.
     *
     * @param item
     *            The item to be replaced; must not be <code>null</code>.
     */
    PlaceholderContributionItem(final IToolBarContributionItem item) {
        item.saveWidgetState();
        id = item.getId();
        storedHeight = item.getCurrentHeight();
        storedWidth = item.getCurrentWidth();
        storedMinimumItems = item.getMinimumItemsToShow();
        storedUseChevron = item.getUseChevron();
    }

    @Override
