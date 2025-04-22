    /**
     * Adds the given preference node as a subnode of the
     * root.
     *
     * @param node the node to add, which must implement
     *   <code>IPreferenceNode</code>
     */
    public void addToRoot(IPreferenceNode node) {
        Assert.isNotNull(node);
        root.add(node);
    }
