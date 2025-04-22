        Assert.isNotNull(item);
        super.itemRemoved(item);
        CoolItem coolItem = findCoolItem(item);
        if (coolItem != null) {
            coolItem.setData(null);
        }
    }

    /**
     * Positions the list iterator to the starting of the next row. By calling
     * next on the returned iterator, it will return the first element of the
     * next row.
     *
     * @param iterator
     *            the list iterator of contribution items
     * @param ignoreCurrentItem
     *            Whether the current item in the iterator should be considered
     *            (as well as subsequent items).
     */
    private void nextRow(ListIterator<IContributionItem> iterator, boolean ignoreCurrentItem) {

        IContributionItem currentElement = null;
        if (!ignoreCurrentItem && iterator.hasPrevious()) {
            currentElement = iterator.previous();
            iterator.next();
        }

        if ((currentElement != null) && (currentElement.isSeparator())) {
            collapseSeparators(iterator);
            return;
        }

        while (iterator.hasNext()) {
            IContributionItem item = iterator.next();
            if (item.isSeparator()) {
                collapseSeparators(iterator);
                return;
            }
        }
    }

    /*
     * Used for debuging. Prints all the items in the internal structures.
     */
    /**
     * Synchronizes the visual order of the cool items in the control with this
     * manager's internal data structures. This method should be called before
     * requesting the order of the contribution items to ensure that the order
     * is accurate.
     * <p>
     * Note that <code>update()</code> and <code>refresh()</code> are
     * converses: <code>update()</code> changes the visual order to match the
     * internal structures, and <code>refresh</code> changes the internal
     * structures to match the visual order.
     * </p>
     */
    public void refresh() {
        if (!coolBarExist()) {
            return;
        }

        ArrayList<IContributionItem> contributionList = getItemList();

        if (contributionList.isEmpty()) {
