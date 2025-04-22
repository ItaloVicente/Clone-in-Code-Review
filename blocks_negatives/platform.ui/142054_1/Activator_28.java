    /**
     * This method is called when the plug-in is stopped
     */
    @Override
	public void stop(BundleContext context)
        throws Exception {
        super.stop(context);
        plugin = null;
    }
