	public static final String DEFAULT_IMAGE = "org.eclipse.jface.wizard.Wizard.pageImage";//$NON-NLS-1$

	private IWizardContainer container = null;

	private List<IWizardPage> pages = new ArrayList<>();

	private boolean needsProgressMonitor = false;

	private boolean forcePreviousAndNextButtons = false;

	private boolean isHelpAvailable = false;

	private Image defaultImage = null;
