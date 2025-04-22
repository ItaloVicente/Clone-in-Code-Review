    /**
     * Constructs a new navigator action group and creates its actions.
     *
     * @param navigator the resource navigator
     */
    public ResourceNavigatorActionGroup(IResourceNavigator navigator) {
        this.navigator = navigator;
        makeActions();
    }
