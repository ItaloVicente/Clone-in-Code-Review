    /**
     * Creates a new preference manager with the given
     * path separator and root node.
     *
     * @param separatorChar the separator character
     * @param rootNode the root node.
     *
     * @since 3.4
     */
    public PreferenceManager(final char separatorChar, PreferenceNode rootNode) {
        separator = new String(new char[] { separatorChar });
        this.root = rootNode;
    }
