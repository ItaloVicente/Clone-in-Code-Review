	@Override
	public void showBusy(boolean busy) {
		if (this.busy != busy) {
			if (busy) {
				getProgressService().incrementBusy();
			} else {
				getProgressService().decrementBusy();
			}
		}
		this.busy = busy;
	}

	public IWorkbenchSiteProgressService getProgressService() {
		IWorkbenchSiteProgressService service = null;
		Object siteService = getSite().getAdapter(IWorkbenchSiteProgressService.class);
		if (siteService != null)
			service = (IWorkbenchSiteProgressService) siteService;
		return service;
	}

