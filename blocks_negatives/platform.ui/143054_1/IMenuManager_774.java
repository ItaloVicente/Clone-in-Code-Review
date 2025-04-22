    /**
     * Finds the manager for the menu at the given path. A path
     * consists of contribution item ids separated by the separator
     * character.  The path separator character is <code>'/'</code>.
     * <p>
     * Convenience for <code>findUsingPath(path)</code> which
     * extracts an <code>IMenuManager</code> if possible.
     * </p>
     *
     * @param path the path string
     * @return the menu contribution item, or <code>null</code>
     *   if there is no such contribution item or if the item does
     *   not have an associated menu manager
     */
    public IMenuManager findMenuUsingPath(String path);
