    }

    /**
     * Add an editor for the given extensions with the specified (possibly null)
     * extended type. The editor is being registered from a plugin
     *
     * @param editor
     *            The description of the editor (as obtained from the plugin
     *            file and built by the registry reader)
     * @param extensions
     *            Collection of file extensions the editor applies to
     * @param filenames
     *            Collection of filenames the editor applies to
     * @param contentTypeVector
     * @param bDefault
     *            Indicates whether the editor should be made the default editor
     *            and hence appear first inside a FileEditorMapping
     *
     * This method is not API and should not be called outside the workbench
     * code.
     */
