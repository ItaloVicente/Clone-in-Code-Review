        return new ActionSetContribution(actionSet.getDesc().getId(), window);
    }

    /**
     * Returns the insertion point for a new contribution item.  Clients should
     * use this item as a reference point for insertAfter.
     *
     * @param startId the reference id for insertion
     * @param sortId the sorting id for the insertion.  If null then the item
     *		will be inserted at the end of all action sets.
     * @param mgr the target menu manager.
     * @param startVsEnd if <code>true</code> the items are added at the start of
     *		action with the same id; else they are added to the end
     * @return the insertion point, or null if not found.
     */
    public static IContributionItem findInsertionPoint(String startId,
            String sortId, IContributionManager mgr, boolean startVsEnd) {
        IContributionItem[] items = mgr.getItems();

        int insertIndex = 0;
        while (insertIndex < items.length) {
            if (startId.equals(items[insertIndex].getId())) {
