	private IAdaptable pageInput;

	private IMenuManager parentMenuManager;

	private boolean replaceEnabled = true;

	private static String PAGE_PROBLEMS_TITLE = WorkbenchMessages.OpenPerspectiveMenu_pageProblemsTitle;

	private static String PAGE_PROBLEMS_MESSAGE = WorkbenchMessages.OpenPerspectiveMenu_errorUnknownInput;

	public OpenPerspectiveMenu(IMenuManager menuManager, IWorkbenchWindow window) {
		this(window);
		this.parentMenuManager = menuManager;
	}

	public OpenPerspectiveMenu(IWorkbenchWindow window) {
		this(window, null);
		showActive(true);
	}

	public OpenPerspectiveMenu(IWorkbenchWindow window, IAdaptable input) {
		super(window, "Open New Page Menu");//$NON-NLS-1$
		this.pageInput = input;
	}

	private boolean canRun() {
		if (openPerspectiveSetting().equals(IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE)) {
