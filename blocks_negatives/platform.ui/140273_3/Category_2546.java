        return id;
    }

    /**
     * Return the elements contained in this category.
     *
     * @return the elements
     */
    public ArrayList getElements() {
        return elements;
    }

    /**
     * Return whether a given object exists in this category.
     *
     * @param o the object to search for
     * @return whether the object is in this category
     */
    public boolean hasElement(Object o) {
        if (elements == null) {
