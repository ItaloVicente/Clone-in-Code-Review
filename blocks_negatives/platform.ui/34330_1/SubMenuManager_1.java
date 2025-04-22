    /* (non-Javadoc)
     * Method declared on IMenuManager.
     *
     * Returns the item passed to us, not the wrapper.
     *
     * We use use the same algorithm as MenuManager.findUsingPath, but unwrap
     * submenus along so that SubMenuManagers are visible.
     */
