        return false;
    }

    /**
     * Returns the menu wrapper for a menu manager.
     * <p>
     * The sub menus within this menu are wrapped within a <code>SubMenuManager</code> to
     * monitor additions and removals.  If the visibility of this menu is modified
     * the visibility of the sub menus is also modified.
     * <p>
     * @param mgr the menu manager to be wrapped
     *
     * @return the menu wrapper
     */
    protected IMenuManager getWrapper(IMenuManager mgr) {
        if (mapMenuToWrapper == null) {
            mapMenuToWrapper = new HashMap<>(4);
        }
        SubMenuManager wrapper = mapMenuToWrapper.get(mgr);
        if (wrapper == null) {
            wrapper = wrapMenu(mgr);
            mapMenuToWrapper.put(mgr, wrapper);
        }
        return wrapper;
    }

    @Override
