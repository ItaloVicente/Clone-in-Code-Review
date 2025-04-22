    /**
     * Removes the given prefreence node if it is managed by
     * this contribution manager.
     *
     * @param node the node to remove
     * @return <code>true</code> if the node was removed,
     *  and <code>false</code> otherwise
     */
    public boolean remove(IPreferenceNode node) {
        Assert.isNotNull(node);
