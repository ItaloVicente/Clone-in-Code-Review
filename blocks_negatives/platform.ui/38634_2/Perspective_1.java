        this.page = page;
        alwaysOnActionSets = new ArrayList(2);
        alwaysOffActionSets = new ArrayList(2);
        hideMenuIDs = new HashSet();
        hideToolBarIDs = new HashSet();
        
        
        mapIDtoViewLayoutRec = new HashMap();
    }


    /**
     * Create the initial list of action sets.
     */
    protected void createInitialActionSets(List outputList, List stringList) {
        ActionSetRegistry reg = WorkbenchPlugin.getDefault()
                .getActionSetRegistry();
        Iterator iter = stringList.iterator();
        while (iter.hasNext()) {
            String id = (String) iter.next();
            IActionSetDescriptor desc = reg.findActionSet(id);
            if (desc != null) {
