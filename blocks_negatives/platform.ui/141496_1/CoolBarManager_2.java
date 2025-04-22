        /* We will allow as many null entries as they like, though there should
         * be none.
         */
        if (itemToAdd == null) {
            return true;
        }

        /* Null identifiers can be expected in generic contribution items.
         */
        String firstId = itemToAdd.getId();
        if (firstId == null) {
            return true;
        }

        IContributionItem[] currentItems = getItems();
        for (IContributionItem currentItem : currentItems) {
            if (currentItem == null) {
                continue;
            }

            String secondId = currentItem.getId();
            if (firstId.equals(secondId)) {
                if (Policy.TRACE_TOOLBAR) {
                    new Exception().printStackTrace(System.out);
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Positions the list iterator to the end of all the separators. Calling
     * <code>next()</code> the iterator should return the immediate object
     * following the last separator.
     *
     * @param iterator
     *            the list iterator.
     */
    private void collapseSeparators(ListIterator<IContributionItem> iterator) {

        while (iterator.hasNext()) {
            IContributionItem item = iterator.next();
            if (!item.isSeparator()) {
                iterator.previous();
                return;
            }
        }
    }

    /**
     * Returns whether the cool bar control has been created and not yet
     * disposed.
     *
     * @return <code>true</code> if the control has been created and not yet
     *         disposed, <code>false</code> otherwise
     */
    private boolean coolBarExist() {
        return coolBar != null && !coolBar.isDisposed();
    }

    /**
     * Creates and returns this manager's cool bar control. Does not create a
     * new control if one already exists.
     *
     * @param parent
     *            the parent control
     * @return the cool bar control
     */
    public CoolBar createControl(Composite parent) {
        Assert.isNotNull(parent);
        if (!coolBarExist()) {
            coolBar = new CoolBar(parent, itemStyle);
            coolBar.setMenu(getContextMenuControl());
            coolBar.setLocked(false);
            update(false);
        }
        return coolBar;
    }

    /**
     * Disposes of this cool bar manager and frees all allocated SWT resources.
     * Notifies all contribution items of the dispose. Note that this method
     * does not clean up references between this cool bar manager and its
     * associated contribution items. Use <code>removeAll</code> for that
     * purpose.
     */
    public void dispose() {
        if (coolBarExist()) {
            coolBar.dispose();
            coolBar = null;
        }
        IContributionItem[] items = getItems();
        for (IContributionItem item : items) {
            item.dispose();
        }
        if (contextMenuManager != null) {
            contextMenuManager.dispose();
            contextMenuManager = null;
        }

    }

    /**
     * Disposes the given cool item.
     *
     * @param item
     *            the cool item to dispose
     */
    private void dispose(CoolItem item) {
        if ((item != null) && !item.isDisposed()) {

            item.setData(null);
            Control control = item.getControl();
            if ((control != null) && !control.isDisposed()) {
                item.setControl(null);
            	control.dispose();
            }
            item.dispose();
        }
    }

    /**
     * Finds the cool item associated with the given contribution item.
     *
     * @param item
     *            the contribution item
     * @return the associated cool item, or <code>null</code> if not found
     */
    private CoolItem findCoolItem(IContributionItem item) {
        CoolItem[] coolItems = (coolBar == null) ? null : coolBar.getItems();
        return findCoolItem(coolItems, item);
    }

    private CoolItem findCoolItem(CoolItem[] items, IContributionItem item) {
        if (items == null) {
