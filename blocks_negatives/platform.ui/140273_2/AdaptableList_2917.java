    /**
     * Removes the given adaptable object from this list.
     *
     * @param adaptable the element to remove
     */
    public void remove(IAdaptable adaptable) {
        Assert.isNotNull(adaptable);
        children.remove(adaptable);
    }
