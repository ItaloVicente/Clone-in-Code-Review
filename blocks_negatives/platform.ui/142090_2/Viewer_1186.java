    /**
     * Internal hook method called when the input to this viewer is
     * initially set or subsequently changed.
     * <p>
     * The default implementation does nothing. Subclassers may override
     * this method to do something when a viewer's input is set.
     * A typical use is populate the viewer.
     * </p>
     *
     * @param input the new input of this viewer, or <code>null</code> if none
     * @param oldInput the old input element or <code>null</code> if there
     *   was previously no input
     */
    protected void inputChanged(Object input, Object oldInput) {
    }
