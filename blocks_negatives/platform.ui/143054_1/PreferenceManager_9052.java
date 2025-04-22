    /**
     * Returns the root node.
     * Note that the root node is a special internal node
     * that is used to collect together all the nodes that
     * have no parent; it is not given out to clients.
     *
     * @return the root node
     */
    protected IPreferenceNode getRoot() {
        return root;
    }
