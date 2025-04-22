	private IWizardContainer container = null;

	private List<IWizardPage> pages = new ArrayList<>();

	private boolean needsProgressMonitor = false;

	private boolean forcePreviousAndNextButtons = false;

	private boolean isHelpAvailable = false;

	private Image defaultImage = null;

	private ImageDescriptor defaultImageDescriptor = JFaceResources.getImageRegistry().getDescriptor(DEFAULT_IMAGE);

	private RGB titleBarColor = null;

	private String windowTitle = null;

	private IDialogSettings dialogSettings = null;

	protected Wizard() {
		super();
	}

	public void addPage(IWizardPage page) {
		pages.add(page);
		page.setWizard(this);
	}

	@Override
