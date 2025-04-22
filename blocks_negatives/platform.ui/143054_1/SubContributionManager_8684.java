        SubContributionItem wrap = wrap(item);
        wrap.setVisible(visible);
        parentMgr.appendToGroup(groupName, wrap);
        itemAdded(item, wrap);
    }

    /**
     * Disposes this sub contribution manager, removing all its items
     * and cleaning up any other resources allocated by it.
     * This must leave no trace of this sub contribution manager
     * in the parent manager.  Subclasses may extend.
     *
     * @since 3.0
     */
    public void disposeManager() {
        Iterator<SubContributionItem> it = mapItemToWrapper.values().iterator();
        while (it.hasNext()) {
            IContributionItem item = it.next();
            item.dispose();
        }
        removeAll();
    }

    @Override
