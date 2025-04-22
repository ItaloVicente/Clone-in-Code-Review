    private void removeAlwaysOn(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (!alwaysOnActionSets.contains(descriptor)) {
            return;
        }
        
        alwaysOnActionSets.remove(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
        }
    }
    
    protected void addAlwaysOff(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (alwaysOffActionSets.contains(descriptor)) {
            return;
        }
        alwaysOffActionSets.add(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
        }
        removeAlwaysOn(descriptor);
    }
    
    protected void addAlwaysOn(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (alwaysOnActionSets.contains(descriptor)) {
            return;
        }
        alwaysOnActionSets.add(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
        }
        removeAlwaysOff(descriptor);
    }
    
    private void removeAlwaysOff(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (!alwaysOffActionSets.contains(descriptor)) {
            return;
        }
        alwaysOffActionSets.remove(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
        }
    }
    
    /**
     * Returns the ActionSets read from perspectiveExtensions in the registry.  
     */
    protected ArrayList getPerspectiveExtensionActionSets() {
		return page.getPerspectiveExtensionActionSets(descriptor.getOriginalId());
    }
    
    public void turnOnActionSets(IActionSetDescriptor[] newArray) {
        for (int i = 0; i < newArray.length; i++) {
            IActionSetDescriptor descriptor = newArray[i];
            
			addActionSet(descriptor);
        }
    }
    
    public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
        for (int i = 0; i < toDisable.length; i++) {
            IActionSetDescriptor descriptor = toDisable[i];
            
            turnOffActionSet(descriptor);
        }
    }
