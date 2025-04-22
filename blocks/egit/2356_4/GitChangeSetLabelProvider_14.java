	public static final String BINDING_CHANGESET_SHORT_MESSAGE = "{short_message}"; //$NON-NLS-1$

	public static final String BINDING_CHANGESET_COMMITTER = "{committer}"; //$NON-NLS-1$

	public static final String BINDING_CHANGESET_AUTHOR = "{author}"; //$NON-NLS-1$

	public static final String BINDING_CHANGESET_DATE = "{date}"; //$NON-NLS-1$

	public static final String DEFAULT_CHANGESET_FORMAT = String.format("[%s] (%s) %s", //$NON-NLS-1$
			BINDING_CHANGESET_AUTHOR,
			BINDING_CHANGESET_DATE,
			BINDING_CHANGESET_SHORT_MESSAGE);

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";  //$NON-NLS-1$

	private IPreferenceStore store = org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore();

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			store.getString(UIPreferences.DATE_FORMAT));

