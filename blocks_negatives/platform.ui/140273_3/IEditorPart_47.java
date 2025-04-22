    /**
     * Returns the site for this editor.
     * This method is equivalent to <code>(IEditorSite) getSite()</code>.
     * <p>
     * The site can be <code>null</code> while the editor is being initialized.
     * After the initialization is complete, this value must be non-<code>null</code>
     * for the remainder of the editor's life cycle.
     * </p>
     *
     * @return the editor site; this value may be <code>null</code> if the editor
     *         has not yet been initialized
     */
    IEditorSite getEditorSite();
