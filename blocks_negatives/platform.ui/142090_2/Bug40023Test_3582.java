    /**
     * Retrieves a menu item matching or starting with the given name from an
     * array of menu items.
     *
     * @param menuItems
     *            The array of menu items to search; must not be <code>null</code>
     * @param text
     *            The text to look for; may be <code>null</code>.
     * @return The menu item, if any is found; <code>null</code> otherwise.
     */
    public static MenuItem getMenuItem(MenuItem[] menuItems, String text) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getText().startsWith(text)) {
                return menuItem;
            }
        }
