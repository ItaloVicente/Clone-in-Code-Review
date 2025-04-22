		partListener = new ActivationListener();
		getViewSite().getPage().addPartListener(partListener);
	}

	@Override
	public void dispose() {
		IViewSite site = getViewSite();
		if (partListener != null && site != null) {
			site.getPage().removePartListener(partListener);
			partListener = null;
		}
		super.dispose();
