        return (IViewSite) getSite();
    }

    /**
     * @see IViewPart#init(IViewSite)
     */
    @Override
	public void init(IViewSite site) throws PartInitException {
        setSite(site);
        callTrace.add("init");
        setSiteInitialized();
        addToolbarContributionItem();
    }

    /**
     * @see IViewPart#init(IViewSite, IMemento)
     */
    @Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
        setSite(site);
        callTrace.add("init");
        setSiteInitialized();
        addToolbarContributionItem();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.tests.api.MockWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
