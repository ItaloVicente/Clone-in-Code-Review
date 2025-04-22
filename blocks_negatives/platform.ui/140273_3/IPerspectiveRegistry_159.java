    /**
     * Sets the default perspective for the workbench to the given perspective id.
     * If non-<code>null</code>, the id must correspond to a perspective extension
     * within the workbench's perspective registry.
     * <p>
     * A <code>null</code> id indicates no default perspective.
     * </p>
     *
     * @param id a perspective id, or <code>null</code>
     */
    void setDefaultPerspective(String id);
