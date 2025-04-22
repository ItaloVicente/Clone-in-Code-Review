    /**
     * Adds the given preference node as a subnode of the
     * node at the given path.
     *
     * @param path the path
     * @param node the node to add
     * @return <code>true</code> if the add was successful,
     *  and <code>false</code> if there is no contribution at
     *  the given path
     */
    public boolean addTo(String path, IPreferenceNode node) {
        IPreferenceNode target = find(path);
        if (target == null) {
