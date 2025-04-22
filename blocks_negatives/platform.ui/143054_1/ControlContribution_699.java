    /**
     * Creates and returns the control for this contribution item
     * under the given parent composite.
     * <p>
     * This framework method must be implemented by concrete
     * subclasses.
     * </p>
     *
     * @param parent the parent composite
     * @return the new control, must not be <code>null</code>
     */
    protected abstract Control createControl(Composite parent);
