    /**
     * Undoes everything that was done by a previous call to create(...), given
     * the object that was returned by create(...).
     *
     * @since 3.1
     *
     * @param previouslyCreatedObject an object that was returned by an equal
     * descriptor in a previous call to createResource(...).
     */
    public abstract void destroyResource(Object previouslyCreatedObject);
