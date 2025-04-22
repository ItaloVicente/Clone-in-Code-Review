    		service.deferUpdates(false);
    	}
    }
    
    public IActionSetDescriptor[] getAlwaysOnActionSets() {
        return (IActionSetDescriptor[]) alwaysOnActionSets.toArray(new IActionSetDescriptor[alwaysOnActionSets.size()]);
    }
    
    public IActionSetDescriptor[] getAlwaysOffActionSets() {
        return (IActionSetDescriptor[]) alwaysOffActionSets.toArray(new IActionSetDescriptor[alwaysOffActionSets.size()]);
    }
	
