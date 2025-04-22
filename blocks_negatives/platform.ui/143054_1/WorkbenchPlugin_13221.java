    }

    /**
     * Return the default instance of the receiver. This represents the runtime plugin.
     * @return WorkbenchPlugin
     * @see AbstractUIPlugin for the typical implementation pattern for plugin classes.
     */
    public static WorkbenchPlugin getDefault() {
        return inst;
    }

    /**
     * Answer the manager that maps resource types to a the
     * description of the editor to use
     * @return IEditorRegistry the editor registry used
     * by this plug-in.
     */

    public IEditorRegistry getEditorRegistry() {
