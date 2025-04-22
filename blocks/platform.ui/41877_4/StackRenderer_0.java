	public static final String MRU_KEY_DEFAULT = "enableMRUDefault"; //$NON-NLS-1$

	public static final String MRU_KEY = "enableMRU"; //$NON-NLS-1$

	public static final String MRU_CONTROLLED_BY_CSS_KEY = "MRUControlledByCSS"; //$NON-NLS-1$

	private static final boolean MRU_CONTROLLED_BY_CSS_DEFAULT = false;

	@Inject
	@Preference(nodePath = "org.eclipse.e4.ui.workbench.renderers.swt")
	private IEclipsePreferences preferences;

