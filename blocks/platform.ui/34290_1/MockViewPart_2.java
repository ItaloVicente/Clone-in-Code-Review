		return (IViewSite) getSite();
	}

	@Override
	public void init(IViewSite site) {
		setSite(site);
		callTrace.add("init");
		setSiteInitialized();
		addToolbarContributionItem();
	}

	@Override
	public void init(IViewSite site, IMemento memento) {
		setSite(site);
		callTrace.add("init");
		setSiteInitialized();
		addToolbarContributionItem();
	}

	@Override
