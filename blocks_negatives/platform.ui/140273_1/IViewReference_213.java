    /**
     * Returns the <code>IViewPart</code> referenced by this object.
     * Returns <code>null</code> if the view was not instantiated or
     * it failed to be restored.  Tries to restore the view
     * if <code>restore</code> is true.
     */
    IViewPart getView(boolean restore);
