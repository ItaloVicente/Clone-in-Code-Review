    /**
     * This class adds the requirement that action delegates
     * loaded on demand implement IViewActionDelegate
     */
    public ViewPluginAction(IConfigurationElement actionElement,
            IViewPart viewPart, String id, int style) {
        super(actionElement, id, style);
        this.viewPart = viewPart;
        registerSelectionListener(viewPart);
    }
