    /**
     * Sets the default editor id for the files that match that
     * specified file name or extension. The specified editor must be
     * defined as an editor for that file name or extension.
     *
     * @param fileNameOrExtension the file name or extension pattern (e.g. "*.xml");
     * @param editorId the editor id or <code>null</code> for no default
     */
    void setDefaultEditor(String fileNameOrExtension, String editorId);
