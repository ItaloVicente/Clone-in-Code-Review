        if (item instanceof IMenuManager) {
            IMenuManager menu = (IMenuManager) item;
            item = getWrapper(menu);
        }

        return item;
    }

    /**
     * <p>
     * The menu returned is wrapped within a <code>SubMenuManager</code> to
     * monitor additions and removals.  If the visibility of this menu is modified
     * the visibility of the submenus is also modified.
     * </p>
     */
    @Override
