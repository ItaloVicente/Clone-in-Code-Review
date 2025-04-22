    /**
     * Returns the default editor for a given file name and with the given content type.
     * <p>
     * The default editor is determined by taking the file extension for the
     * file and obtaining the default editor for that extension.
     * </p>
     *
     * @param fileName the file name in the system
     * @param contentType the content type or <code>null</code> for the unknown content type
     * @return the descriptor of the default editor, or <code>null</code> if not
     *   found
     * @since 3.1
     */
    IEditorDescriptor getDefaultEditor(String fileName, IContentType contentType);
