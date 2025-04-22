    /**
     * Sets the input to this editor and fires a PROP_INPUT property change if
     * the input has changed.  This is the convenience method implementation.
     *
     * <p>Note that firing a property change may cause other objects to reach back
     * and invoke methods on the editor. Care should be taken not to call this method
     * until the editor has fully updated its internal state to reflect the
     * new input.</p>
     *
     * @since 3.2
     *
     * @param input the editor input
     */
    protected void setInputWithNotify(IEditorInput input) {
