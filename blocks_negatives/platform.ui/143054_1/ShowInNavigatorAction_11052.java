    /**
     * Create a new instance of this class.
     *
     * @param page the page
     * @param viewer the viewer
     */
    public ShowInNavigatorAction(IWorkbenchPage page, ISelectionProvider viewer) {
        super(viewer, ResourceNavigatorMessages.ShowInNavigator_text);
        Assert.isNotNull(page);
        this.page = page;
        setDescription(ResourceNavigatorMessages.ShowInNavigator_toolTip);
        page.getWorkbenchWindow().getWorkbench().getHelpSystem().setHelp(this,
