        getParentMenuManager().updateAll(force);
    }

    /**
     * Wraps a menu manager in a sub menu manager, and returns the new wrapper.
     * @param menu the menu manager to wrap
     * @return the new wrapped menu manager
     */
    protected SubMenuManager wrapMenu(IMenuManager menu) {
        SubMenuManager mgr = new SubMenuManager(menu);
        mgr.setVisible(isVisible());
        return mgr;
    }
