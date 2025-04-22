    private IEditorPart activeEditor;

    /**
     * Creates a new action with the given text.
     *
     * @param text the string used as the text for the action,
     *   or <code>null</code> if there is no text
     * @param window the workbench window this action is
     *   registered with.
     */
    protected ActiveEditorAction(String text, IWorkbenchWindow window) {
        super(text, window);
        updateState();
    }

    /**
     * Notification that the active editor tracked
     * by the action is being activated.
     *
     * Subclasses may override.
     */
    protected void editorActivated(IEditorPart part) {
    }

    /**
     * Notification that the active editor tracked
     * by the action is being deactivated.
     *
     * Subclasses may override.
     */
    protected void editorDeactivated(IEditorPart part) {
    }

    /**
     * Return the active editor
     *
     * @return the page's active editor, and <code>null</code>
     *		if no active editor or no active page.
     */
    public final IEditorPart getActiveEditor() {
        return activeEditor;
    }

    @Override
