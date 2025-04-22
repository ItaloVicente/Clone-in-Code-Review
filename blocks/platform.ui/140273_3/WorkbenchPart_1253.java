		titleImage = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
	}

	protected void setSite(IWorkbenchPartSite site) {
		checkSite(site);
		this.partSite = site;
	}

	protected void checkSite(IWorkbenchPartSite site) {
	}

	@Deprecated
