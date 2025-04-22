	private String name;

	private IWizard wizard = null;

	private boolean isPageComplete = true;

	private IWizardPage previousPage = null;

	protected WizardPage(String pageName) {
		this(pageName, null, (ImageDescriptor) null);
	}

	protected WizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(title, titleImage);
		Assert.isNotNull(pageName); // page name must not be null
		name = pageName;
	}

	@Override
