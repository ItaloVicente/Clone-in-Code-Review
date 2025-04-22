    /**
     * Finds and returns the contribution node at the given path.
     *
     * @param path the path
     * @return the node, or <code>null</code> if none
     */
    public IPreferenceNode find(String path) {
       return find(path,root);
    }
