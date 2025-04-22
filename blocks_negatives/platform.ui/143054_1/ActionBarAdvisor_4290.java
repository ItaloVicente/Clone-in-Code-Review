    /**
     * Returns whether the menu with the given id is an application menu of the
     * given window. This is used during OLE "in place" editing.  Application
     * menus should be preserved during menu merging. All other menus may be
     * removed from the window.
     * <p>
     * The default implementation returns false. Subclasses may override.
     * </p>
     *
     * @param menuId the menu id
     * @return <code>true</code> for application menus, and <code>false</code>
     * for part-specific menus
     */
    public boolean isApplicationMenu(String menuId) {
        return false;
    }
