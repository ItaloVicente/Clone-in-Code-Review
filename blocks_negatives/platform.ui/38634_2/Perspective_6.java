    }

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
