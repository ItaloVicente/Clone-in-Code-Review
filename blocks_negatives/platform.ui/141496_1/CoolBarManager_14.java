        this.contextMenuManager = (MenuManager) contextMenuManager;
        if (coolBar != null) {
            coolBar.setMenu(getContextMenuControl());
        }
    }

    /**
     * Replaces the current items with the given items.
     * Forces an update.
     *
     * @param newItems the items with which to replace the current items
     */
    public void setItems(IContributionItem[] newItems) {
        if (coolBar != null) {
            CoolItem[] coolItems = coolBar.getItems();
            for (CoolItem coolItem : coolItems) {
                dispose(coolItem);
            }
        }
        internalSetItems(newItems);
        update(true);
    }

    @Override
