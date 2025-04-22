    /**
     * Clones an existing perspective.
     *
     * @param id the id for the cloned perspective, which must not already be used by
     *   any registered perspective
     * @param label the label assigned to the cloned perspective
     * @param desc the perspective to clone
     * @return the cloned perspective descriptor
     * @throws IllegalArgumentException if there is already a perspective with the given id
     *
     * @since 3.0
     */
    public IPerspectiveDescriptor clonePerspective(String id, String label,
            IPerspectiveDescriptor desc) throws IllegalArgumentException;
