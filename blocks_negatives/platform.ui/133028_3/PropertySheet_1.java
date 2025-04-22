    @Override
	public void init(IViewSite site) throws PartInitException {
   		site.getPage().addPostSelectionListener(this);
   		super.init(site);
    }

