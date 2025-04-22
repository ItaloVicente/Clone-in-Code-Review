    /**
     * Creates a new instance of the class.
     */
    public ResourceNavigatorAction(IResourceNavigator navigator, String label) {
        super(navigator.getViewer(), label);
        this.navigator = navigator;
    }
