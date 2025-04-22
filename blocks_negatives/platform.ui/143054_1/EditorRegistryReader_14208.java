    /**
     * Returns all editor descriptors of this mapping, not filtered by activities.
     */
    IEditorDescriptor[] getUnfilteredEditors() {
    	return editors.toArray(new IEditorDescriptor[editors.size()]);
    }
