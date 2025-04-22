        listeners.add(listener);
    }

    /**
     * Creates and returns an SWT context menu control for this menu,
     * and installs all registered contributions.
     * Does not create a new control if one already exists.
     * <p>
     * Note that the menu is not expected to be dynamic.
     * </p>
     *
     * @param parent the parent control
     * @return the menu control
     */
    public Menu createContextMenu(Control parent) {
        if (!menuExist()) {
            menu = new Menu(parent);
            menu.setData(MANAGER_KEY, this);
            initializeMenu();
        }
        return menu;
    }

    /**
     * Creates and returns an SWT menu bar control for this menu,
     * for use in the given <code>Decorations</code>, and installs all registered
     * contributions. Does not create a new control if one already exists.
     *
     * @param parent the parent decorations
     * @return the menu control
     * @since 2.1
     */
    public Menu createMenuBar(Decorations parent) {
        if (!menuExist()) {
            menu = new Menu(parent, SWT.BAR);
            menu.setData(MANAGER_KEY, this);
            update(false);
        }
        return menu;
    }

    /**
     * Creates and returns an SWT menu bar control for this menu, for use in the
     * given <code>Shell</code>, and installs all registered contributions. Does not
     * create a new control if one already exists. This implementation simply calls
     * the <code>createMenuBar(Decorations)</code> method
     *
     * @param parent the parent decorations
     * @return the menu control
     * @deprecated use <code>createMenuBar(Decorations)</code> instead.
     */
    @Deprecated
