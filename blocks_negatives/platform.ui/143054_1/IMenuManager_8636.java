    /**
     * Finds the contribution item at the given path. A path
     * consists of contribution item ids separated by the separator
     * character. The path separator character is <code>'/'</code>.
     *
     * @param path the path string
     * @return the contribution item, or <code>null</code> if there is no
     *   such contribution item
     */
    public IContributionItem findUsingPath(String path);
