	 * This method is called during workbench initialization prior to any
	 * windows being opened. Clients must not call this method directly
	 * (although super calls are okay). The default implementation does nothing.
	 * Subclasses may override. Typical clients will use the configurer passed
	 * in to tweak the workbench. If further tweaking is required in the future,
	 * the configurer may be obtained using <code>getWorkbenchConfigurer</code>.
