     * Returns the data of the Text node of the memento. Each memento is allowed
     * only one Text node.
     *
     * @return the data of the Text node of the memento, or <code>null</code>
     * if the memento has no Text node.
     * @since 2.0
     */
    String getTextData();

    /**
     * Returns an array of all the attribute keys of the memento. This will not
     * be <code>null</code>. If there are no keys, an array of length zero will
     * be returned.
     * @return an array with all the attribute keys of the memento
     * @since 3.4
     */
