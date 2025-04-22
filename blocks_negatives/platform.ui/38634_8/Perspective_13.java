    		service.deferUpdates(false);
    	}
    }
    
    public IActionSetDescriptor[] getAlwaysOnActionSets() {
        return (IActionSetDescriptor[]) alwaysOnActionSets.toArray(new IActionSetDescriptor[alwaysOnActionSets.size()]);
    }
    
    public IActionSetDescriptor[] getAlwaysOffActionSets() {
        return (IActionSetDescriptor[]) alwaysOffActionSets.toArray(new IActionSetDescriptor[alwaysOffActionSets.size()]);
    }
	
	/**	@return a Collection of IDs of items to be hidden from the menu bar	*/
	public Collection getHiddenMenuItems() {
		return hideMenuIDs;
