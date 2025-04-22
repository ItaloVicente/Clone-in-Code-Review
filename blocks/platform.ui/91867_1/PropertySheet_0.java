		if (part == null) {
			return false;
		}
    	IWorkbenchPartSite site = part.getSite();
		if (site == null) {
			return false;
		}
		String partID = site.getId();
