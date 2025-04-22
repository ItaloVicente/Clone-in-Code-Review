    /**
     * Returns the default editor. The default editor always exist.
     *
     * @return the descriptor of the default editor
     * @deprecated The system external editor is the default editor.
     * Use <code>findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID)</code>
     * instead.
     */
    @Deprecated IEditorDescriptor getDefaultEditor();
