    /**
     * Creates a new action set from this descriptor.
     * <p>
     * [Issue: Consider throwing WorkbenchException rather than CoreException.]
     * </p>
     *
     * @return the new action set
     * @exception CoreException if the action set cannot be created
     */
    IActionSet createActionSet() throws CoreException;
