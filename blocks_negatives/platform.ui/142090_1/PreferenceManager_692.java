    /**
     * Creates a new preference manager with the given
     * path separator.
     *
     * @param separatorChar
     */
    public PreferenceManager(final char separatorChar) {
    	this(separatorChar, new PreferenceNode(ROOT_NODE_ID));
    }
