	public static final String DEFAULT_IMAGE = "org.eclipse.jface.wizard.Wizard.pageImage";//$NON-NLS-1$

	private IWizardContainer container = null;

	private List<IWizardPage> pages = new ArrayList<IWizardPage>();

	private boolean needsProgressMonitor = false;

	private boolean forcePreviousAndNextButtons = false;

	private boolean isHelpAvailable = false;

	private Image defaultImage = null;

	private ImageDescriptor defaultImageDescriptor = JFaceResources
			.getImageRegistry().getDescriptor(DEFAULT_IMAGE);

	private RGB titleBarColor = null;

	private String windowTitle = null;

	private IDialogSettings dialogSettings = null;

	protected Wizard() {
		super();
	}
