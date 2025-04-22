        return contextMenuManager;
    }

    /**
     * Returns the cool bar control for this manager.
     *
     * @return the cool bar control, or <code>null</code> if none
     */
    public CoolBar getControl() {
        return coolBar;
    }

    /**
     * Returns an array list of all the contribution items in the manager.
     *
     * @return an array list of contribution items.
     */
    private ArrayList<IContributionItem> getItemList() {
        IContributionItem[] cbItems = getItems();
        ArrayList<IContributionItem> list = new ArrayList<>(cbItems.length);
        for (IContributionItem cbItem : cbItems) {
            list.add(cbItem);
        }
        return list;
    }

    @Override
