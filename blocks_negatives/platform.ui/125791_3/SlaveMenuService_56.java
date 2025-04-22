	/**
	 * Disposes contributions created by service for given part. See bug 537046.
	 *
	 * @param site
	 * @param part
	 */
	@Override
	public void clearContributions(PartSite site, MPart part) {
		if (parentService instanceof IMenuServiceWorkaround) {
			IMenuServiceWorkaround service = (IMenuServiceWorkaround) parentService;
			service.clearContributions(site, part);
		}
	}

