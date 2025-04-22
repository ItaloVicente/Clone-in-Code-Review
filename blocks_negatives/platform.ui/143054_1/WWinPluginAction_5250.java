    /**
     * Constructs a new <code>WWinPluginAction</code> object.
     *
     * @param actionElement the configuration element
     * @param window the window to contribute to
     * @param id the identifier
     * @param style the style
     */
    public WWinPluginAction(IConfigurationElement actionElement,
            IWorkbenchWindow window, String id, int style) {
        super(actionElement, id, style);
        this.window = window;
