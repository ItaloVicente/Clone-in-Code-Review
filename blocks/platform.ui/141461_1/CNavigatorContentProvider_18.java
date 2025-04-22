	    for (Object cChildren1 : cChildren) {
		if (cChildren1 instanceof CElement) {
		    IResource resource = ((CElement) cChildren1).getResource();
		    if (resource != null) {
			proposedChildren.remove(resource);
		    }
		    proposedChildren.add(cChildren1);
		} else if (cChildren1 != null) {
		    proposedChildren.add(cChildren1);
