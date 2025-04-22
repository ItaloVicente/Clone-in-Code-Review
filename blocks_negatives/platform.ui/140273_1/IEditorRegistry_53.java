    /**
     * Returns a list of mappings from file type to editor.  The resulting list
     * is sorted in ascending order by file extension.
     * <p>
     * Each mapping defines an extension and the set of editors that are
     * available for that type. The set of editors includes those registered
     * via plug-ins and those explicitly associated with a type by the user
     * in the workbench preference pages.
     * </p>
     *
     * @return a list of mappings sorted alphabetically by extension
     */
    IFileEditorMapping[] getFileEditorMappings();
