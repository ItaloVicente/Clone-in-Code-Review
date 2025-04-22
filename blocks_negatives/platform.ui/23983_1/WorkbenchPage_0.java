    private ListenerList propertyChangeListeners = new ListenerList();

    private IActionBars actionBars;
    
    private ActionSetManager actionSets;

    private NavigationHistory navigationHistory = new NavigationHistory(this);
    

    /**
     * If we're in the process of activating a part, this points to the new part.
     * Otherwise, this is null.
     */
    private IWorkbenchPartReference partBeingActivated = null;
    
    
    
    private IPropertyChangeListener workingSetPropertyChangeListener = new IPropertyChangeListener() {
        /*
         * Remove the working set from the page if the working set is deleted.
         */
        public void propertyChange(PropertyChangeEvent event) {
            String property = event.getProperty();
            if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
            		if(event.getOldValue().equals(workingSet)) {
						setWorkingSet(null);
					}
            		
