	    switch (value) {
		case "hasStagedChanges": //$NON-NLS-1$
		    return Property.HAS_STAGED_CHANGES;
		case "hasUnstagedChanges": //$NON-NLS-1$
		    return Property.HAS_UNSTAGED_CHANGES;
		case "hasNotIgnoredResources": //$NON-NLS-1$
		    return Property.HAS_NOT_IGNORED_RESOURCES;
		case "hasTrackedResources": //$NON-NLS-1$
		    return Property.HAS_TRACKED_RESOURCES;
	    	default:
		    break;
	    }
