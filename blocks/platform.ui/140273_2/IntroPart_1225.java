	@Override
	public void init(IIntroSite site, IMemento memento) throws PartInitException {
		setSite(site);
	}

	protected void setSite(IIntroSite site) {
		this.partSite = site;
	}

	@Override
