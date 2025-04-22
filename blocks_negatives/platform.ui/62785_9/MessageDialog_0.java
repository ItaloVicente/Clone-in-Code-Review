	 * @see #open(int, Shell, String, String, int)
	 */
    public final static int WARNING = 4;

    /**
     * Constant for a simple dialog with the question image and OK/Cancel buttons (value 5).
     *
     * @see #open(int, Shell, String, String, int)
     * @since 3.5
     */
    public final static int CONFIRM = 5;

    /**
     * Constant for a simple dialog with the question image and Yes/No/Cancel buttons (value 6).
     *
     * @see #open(int, Shell, String, String, int)
     * @since 3.5
     */
    public final static int QUESTION_WITH_CANCEL = 6;

    /**
     * Labels for buttons in the button bar (localized strings).
     */
    private String[] buttonLabels;

    /**
     * The buttons. Parallels <code>buttonLabels</code>.
     */
    private Button[] buttons;

    /**
     * Index into <code>buttonLabels</code> of the default button.
     */
    private int defaultButtonIndex;

    /**
     * Dialog title (a localized string).
     */
    private String title;

    /**
     * Dialog title image.
     */
    private Image titleImage;

    /**
     * Image, or <code>null</code> if none.
     */
    private Image image = null;

    /**
     * The custom dialog area.
     */
    private Control customArea;
