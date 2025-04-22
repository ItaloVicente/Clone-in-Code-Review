        return parentMgr.isEmpty();
    }

    /**
     * Returns whether the contribution list is visible.
     * If the visibility is <code>true</code> then each item within the manager
     * appears within the parent manager.  Otherwise, the items are not visible.
     *
     * @return <code>true</code> if the manager is visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Notifies that an item has been added.
     * <p>
     * Subclasses are not expected to override this method.
     * </p>
     *
     * @param item the item contributed by the client
     * @param wrap the item contributed to the parent manager as a proxy for the item
     *      contributed by the client
     */
    protected void itemAdded(IContributionItem item, SubContributionItem wrap) {
    	item.setParent(this);
        mapItemToWrapper.put(item, wrap);
    }

    /**
     * Notifies that an item has been removed.
     * <p>
     * Subclasses are not expected to override this method.
     * </p>
     *
     * @param item the item contributed by the client
     */
    protected void itemRemoved(IContributionItem item) {
        mapItemToWrapper.remove(item);
        item.setParent(null);
    }

    /**
     * @return fetch all enumeration of wrappers for the item
     * @deprecated Use getItems(String value) instead.
     */
    @Deprecated
