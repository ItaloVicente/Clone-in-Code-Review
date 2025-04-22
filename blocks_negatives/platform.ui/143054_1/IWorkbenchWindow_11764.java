    /**
     * Returns whether the specified menu is an application menu as opposed to
     * a part menu. Application menus contain items which affect the workbench
     * or window. Part menus contain items which affect the active part (view
     * or editor).
     * <p>
     * This is typically used during "in place" editing. Application menus
     * should be preserved during menu merging. All other menus may be removed
     * from the window.
     * </p>
     *
     * @param menuId
     *            the menu id
     * @return <code>true</code> if the specified menu is an application
     *         menu, and <code>false</code> if it is not
     */
    boolean isApplicationMenu(String menuId);
