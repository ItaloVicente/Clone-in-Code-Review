	LoggerFactory factory;
	Logger logger;

	@Reference
	private IExtensionRegistry registry;


	@Reference
	private volatile List<IModelProcessorContribution> processorContributions;

	BundleContext bundleContext;

	BundleListener bundleListener = new BundleListener() {

		@Override
		public void bundleChanged(BundleEvent event) {
			if (event.getBundle().getHeaders("").get(MODEL_FRAGMENT_HEADER) != null) { //$NON-NLS-1$
				if (event.getType() == BundleEvent.RESOLVED || event.getType() == BundleEvent.STARTING
						|| event.getType() == BundleEvent.STARTED) {
					ModelFragmentWrapper wrapper = getModelFragmentWrapperFromBundle(event.getBundle());
					if (wrapper != null) {
						uiSync.asyncExec(() -> processFragmentWrappers(Arrays.asList(wrapper)));
					}
				} else if (event.getType() == BundleEvent.STOPPED) {
					uiSync.asyncExec(() -> {
						ModelFragmentWrapper wrapper = getModelFragmentWrapperFromBundle(event.getBundle());
						if (wrapper != null) {
							for (MApplicationElement appElement : wrapper.getModelFragment().getElements()) {
								if (appElement instanceof MUIElement) {
									MUIElement element = (MUIElement) appElement;
									element.setToBeRendered(false);
									if (element.getParent() != null) {
										element.getParent().getChildren().remove(element);
									}
								}
							}
						}
					});
				}
			}
		}
	};

	@Activate
	void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;

		this.bundleContext.addBundleListener(this.bundleListener);
	}

	@Deactivate
	void deactivate() {
		this.bundleContext.removeBundleListener(this.bundleListener);
	}


