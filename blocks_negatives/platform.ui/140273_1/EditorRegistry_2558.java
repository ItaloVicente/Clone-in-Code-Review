    /**
     * Return an instance of the receiver. Adds listeners into the extension
     * registry for dynamic UI purposes.
     * @param contentTypeManager
     */
    public EditorRegistry(IContentTypeManager contentTypeManager) {
        super();
        this.contentTypeManager = contentTypeManager;
        initializeFromStorage();
        IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
        tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));
