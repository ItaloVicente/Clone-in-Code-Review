	private BundleListener bundleListener = new BundleListener() {
		@Override
		public void bundleChanged(BundleEvent event) {
			NavigatorSaveablesService.bundleChanged(event);
		}
	};
