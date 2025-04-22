    protected PerspectiveDescriptor descriptor;

    protected WorkbenchPage page;

    protected boolean editorHidden = false;
    protected boolean editorAreaRestoreOnUnzoom = false;
    protected int editorAreaState = IWorkbenchPage.STATE_RESTORED;

    
    protected ArrayList alwaysOnActionSets;

    protected ArrayList alwaysOffActionSets;
    
    /**	IDs of menu items the user has chosen to hide	*/
    protected Collection hideMenuIDs;
    
    /**	IDs of toolbar items the user has chosen to hide	*/
    protected Collection hideToolBarIDs;


    private Map mapIDtoViewLayoutRec;

    protected boolean fixed;
