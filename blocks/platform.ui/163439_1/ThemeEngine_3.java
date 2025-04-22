
	private void registerResourceLocator(IConfigurationElement ce) {
		for (IConfigurationElement resourceEl : ce.getChildren("osgiresourcelocator")) {
			String uri = resourceEl.getAttribute("uri");
			if (uri != null) {
				registerResourceLocator(new OSGiResourceLocator(uri));
			}
