    /**
     * This class adds the requirement that action delegates
     * loaded on demand implement IViewActionDelegate
     */
    public EditorPluginAction(IConfigurationElement actionElement,
            IEditorPart part, String id, int style) {
        super(actionElement, id, style);
        if (part != null) {
