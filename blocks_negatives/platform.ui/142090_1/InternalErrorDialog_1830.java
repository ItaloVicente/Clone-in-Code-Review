    private Throwable detail;

    private int detailButtonID = -1;

    private Text text;

    private int defaultButtonIndex = 0;

    /**
     * Size of the text in lines.
     */
    private static final int TEXT_LINE_COUNT = 15;

    /**
     * Create a new dialog.
     *
     * @param parentShell the parent shell
     * @param dialogTitle the  title
     * @param dialogTitleImage the title image
     * @param dialogMessage the message
     * @param detail the error to display
     * @param dialogImageType the type of image
     * @param dialogButtonLabels the button labels
     * @param defaultIndex the default selected button index
     */
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
