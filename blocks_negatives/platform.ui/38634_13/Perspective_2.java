    /**
     * ViewManager constructor comment.
     */
	protected Perspective(WorkbenchPage page) {
        this.page = page;
        alwaysOnActionSets = new ArrayList(2);
        alwaysOffActionSets = new ArrayList(2);
        hideMenuIDs = new HashSet();
        hideToolBarIDs = new HashSet();
        
        
        mapIDtoViewLayoutRec = new HashMap();
    }

