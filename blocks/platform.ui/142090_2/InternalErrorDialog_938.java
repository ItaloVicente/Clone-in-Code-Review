	private Throwable detail;

	private int detailButtonID = -1;

	private Text text;

	private int defaultButtonIndex = 0;

	private static final int TEXT_LINE_COUNT = 15;

	public InternalErrorDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, Throwable detail,
			int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, defaultIndex, dialogButtonLabels);
		defaultButtonIndex = defaultIndex;
		this.detail = detail;
		setShellStyle(getShellStyle() | SWT.APPLICATION_MODAL);
	}

	@Override
