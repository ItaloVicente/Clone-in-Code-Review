		IStatusLineManager {

	public static final String BEGIN_GROUP = "BEGIN_GROUP"; //$NON-NLS-1$

	public static final String MIDDLE_GROUP = "MIDDLE_GROUP"; //$NON-NLS-1$

	public static final String END_GROUP = "END_GROUP"; //$NON-NLS-1$

	private Composite statusLine = null;

	public StatusLineManager() {
		add(new GroupMarker(BEGIN_GROUP));
		add(new GroupMarker(MIDDLE_GROUP));
		add(new GroupMarker(END_GROUP));
	}

	public Control createControl(Composite parent) {
		return createControl(parent, SWT.NONE);
	}

	public Control createControl(Composite parent, int style) {
		if (!statusLineExist() && parent != null) {
			statusLine = new StatusLine(parent, style);
			update(false);
		}
		return statusLine;
	}

	public void dispose() {
		if (statusLineExist()) {
