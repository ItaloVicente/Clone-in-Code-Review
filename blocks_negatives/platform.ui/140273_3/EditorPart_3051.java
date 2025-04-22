    /**
     * Sets the input to this editor.  This method simply updates the internal
     * member variable.
     *
     * <p>Unlike most of the other set methods on this class, this method does
     * not fire a property change. Clients that call this method from a subclass
     * must ensure that they fire an IWorkbenchPartConstants.PROP_INPUT property
     * change after calling this method but before leaving whatever public method
     * they are in. Clients that expose this method as public API must fire
     * the property change within their implementation of setInput.</p>
     *
     * <p>Note that firing a property change may cause listeners to immediately
     * reach back and call methods on this editor. Care should be taken not to
     * fire the property change until the editor has fully updated its internal
     * state to reflect the new input.</p>
     *
     * @param input the editor input
     *
     * @see #setInputWithNotify(IEditorInput)
     */
    protected void setInput(IEditorInput input) {
    	Assert.isLegal(input != null);
        editorInput = input;
    }
