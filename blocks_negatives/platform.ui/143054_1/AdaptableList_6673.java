    /**
     * Adds the given adaptable object to this list.
     *
     * @param adaptable the new element
     * @return this list
     */
    public AdaptableList add(IAdaptable adaptable) {
        Assert.isNotNull(adaptable);
        children.add(adaptable);
        return this;
    }
