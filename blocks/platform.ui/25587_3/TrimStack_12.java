	private MAddon minMaxAddon;

	@Inject
	private void setMinMaxElement(MApplication theApp) {
		for (MAddon addon : theApp.getAddons()) {
			String uri = addon.getContributionURI();
			if (uri != null && uri.contains("MinMaxAddon")) { //$NON-NLS-1$
				minMaxAddon = addon;
			}
		}
	}

