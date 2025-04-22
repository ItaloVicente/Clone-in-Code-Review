    /**
     * The control for this dialog page.
     */
    private Control control;

    /**
     * Optional title; <code>null</code> if none.
     *
     * @see #setTitle
     */
    private String title = null;

    /**
     * Optional description; <code>null</code> if none.
     *
     * @see #setDescription
     */
    private String description = null;

    /**
     * Cached image; <code>null</code> if none.
     *
     * @see #setImageDescriptor(ImageDescriptor)
     */
    private Image image = null;

    /**
     * Optional image; <code>null</code> if none.
     *
     * @see #setImageDescriptor(ImageDescriptor)
     */
    private ImageDescriptor imageDescriptor = null;

    /**
     * The current message; <code>null</code> if none.
     */
    private String message = null;

    /**
     * The current message type; default value <code>NONE</code>.
     */
    private int messageType = NONE;

    /**
     * The current error message; <code>null</code> if none.
     */
    private String errorMessage = null;

    /**
     * Font metrics to use for determining pixel sizes.
     */
    private FontMetrics fontMetrics;

    /**
     * Creates a new empty dialog page.
     */
    protected DialogPage() {
    }

    /**
     * Creates a new dialog page with the given title.
     *
     * @param title
     *            the title of this dialog page, or <code>null</code> if none
     */
    protected DialogPage(String title) {
        this.title = title;
    }

    /**
     * Creates a new dialog page with the given title and image.
     *
     * @param title
     *            the title of this dialog page, or <code>null</code> if none
     * @param image
     *            the image for this dialog page, or <code>null</code> if none
     */
    protected DialogPage(String title, ImageDescriptor image) {
        this(title);
        imageDescriptor = image;
    }

    /**
     * Returns the number of pixels corresponding to the height of the given
     * number of characters.
     * <p>
     * This method may only be called after <code>initializeDialogUnits</code>
     * has been called.
     * </p>
     * <p>
     * Clients may call this framework method, but should not override it.
     * </p>
     *
     * @param chars
     *            the number of characters
     * @return the number of pixels
     */
    protected int convertHeightInCharsToPixels(int chars) {
        if (fontMetrics == null) {
