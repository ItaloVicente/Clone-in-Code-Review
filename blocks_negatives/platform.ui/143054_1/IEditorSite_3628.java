    /**
     * Returns the action bar contributor for this editor.
     * <p>
     * An action contributor is responsable for the creation of actions.
     * By design, this contributor is used for one or more editors of the same type.
     * Thus, the contributor returned by this method is not owned completely
     * by the editor - it is shared.
     * </p>
     *
     * @return the editor action bar contributor, or <code>null</code> if none exists
     */
    public IEditorActionBarContributor getActionBarContributor();
