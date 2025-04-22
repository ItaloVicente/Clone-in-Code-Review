	
    protected int compareNames(IResource resource1, IResource resource2) {
    	return icuCollator.compare(resource1.getName(), resource2.getName());
    }

	
