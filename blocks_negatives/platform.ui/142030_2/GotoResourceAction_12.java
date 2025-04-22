    /**
     * Creates a new instance of the class.
     *
     * @param navigator the navigator
     * @param label the label
     * @since 2.0
     */
    public GotoResourceAction(IResourceNavigator navigator, String label) {
        super(navigator, label);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                INavigatorHelpContextIds.GOTO_RESOURCE_ACTION);
    }
