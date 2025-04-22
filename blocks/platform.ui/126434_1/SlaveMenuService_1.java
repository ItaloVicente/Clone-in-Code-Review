	@Override
	public void clearContributions(PartSite site, MPart part) {
		if (parentService instanceof IMenuServiceWorkaround) {
			IMenuServiceWorkaround service = (IMenuServiceWorkaround) parentService;
			service.clearContributions(site, part);
		}
	}

