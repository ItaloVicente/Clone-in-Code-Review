
	private NewWizardMenu newWizardMenu;

	private StatusLineContributionItem statusLineItem;

	private Preferences.IPropertyChangeListener prefListener;

	private IPropertyChangeListener propPrefListener;

	private IPageListener pageListener;

	private IResourceChangeListener resourceListener;

	private boolean isDisposed = false;

	public WorkbenchActionBuilder(IActionBarConfigurer configurer) {
		super(configurer);
		window = configurer.getWindowConfigurer().getWindow();
	}

	private IWorkbenchWindow getWindow() {
		return window;
	}

	private void hookListeners() {

		pageListener = new IPageListener() {
			@Override
