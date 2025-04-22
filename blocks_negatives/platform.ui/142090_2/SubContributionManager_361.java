        mapItemToWrapper.clear();
    }

    /**
     * Sets the visibility of the manager.  If the visibility is <code>true</code>
     * then each item within the manager appears within the parent manager.
     * Otherwise, the items are not visible.
     *
     * @param visible the new visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (mapItemToWrapper.size() > 0) {
            Iterator<SubContributionItem> it = mapItemToWrapper.values().iterator();
            while (it.hasNext()) {
                IContributionItem item = it.next();
                item.setVisible(visible);
            }
            parentMgr.markDirty();
        }
    }

    /**
     * Wraps a contribution item in a sub contribution item, and returns the new wrapper.
     * @param item the contribution item to be wrapped
     * @return the wrapped item
     */
    protected SubContributionItem wrap(IContributionItem item) {
        return new SubContributionItem(item);
    }

    /**
     * Unwraps a nested contribution item. If the contribution item is an
     * instance of <code>SubContributionItem</code>, then its inner item is
     * returned. Otherwise, the item itself is returned.
     *
     * @param item
     *            The item to unwrap; may be <code>null</code>.
     * @return The inner item of <code>item</code>, if <code>item</code> is
     *         a <code>SubContributionItem</code>;<code>item</code>
     *         otherwise.
     */
    protected IContributionItem unwrap(IContributionItem item) {
        if (item instanceof SubContributionItem) {
            return ((SubContributionItem) item).getInnerItem();
        }

        return item;
    }
