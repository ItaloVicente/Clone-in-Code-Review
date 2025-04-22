	@Inject
	private EModelService modelService;

	private ISWTResourceUtilities resUtils = null;

	@Inject
	void setResourceUtils(IResourceUtilities<ImageDescriptor> utils) {
		resUtils = (ISWTResourceUtilities) utils;
	}

	@Inject
	@Optional
	private Logger logger;

	private IMenuListener menuListener = new IMenuListener() {
		@Override
		public void menuAboutToShow(IMenuManager manager) {
			update(null);
		}
	};

