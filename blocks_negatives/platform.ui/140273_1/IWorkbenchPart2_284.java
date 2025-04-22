    /**
     * Returns the content description of this part. The content description is an optional
     * user-readable string that describes what is currently being displayed in the part.
     * By default, the workbench will display the content description in a line
     * near the top of the view or editor.
     * An empty string indicates no content description
     * text. If this value changes the part must fire a property listener event
     * with {@link IWorkbenchPartConstants#PROP_CONTENT_DESCRIPTION}.
     *
     * @return the content description of this part (not <code>null</code>)
     */
    String getContentDescription();
