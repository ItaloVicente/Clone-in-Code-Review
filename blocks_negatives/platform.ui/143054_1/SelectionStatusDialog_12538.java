    private MessageLine fStatusLine;

    private IStatus fLastStatus;

    private Image fImage;

    private boolean fStatusLineAboveButtons = false;

    /**
     * Creates an instance of a <code>SelectionStatusDialog</code>.
     * @param parent
     */
    public SelectionStatusDialog(Shell parent) {
        super(parent);
    }

    /**
     * Controls whether status line appears to the left of the buttons (default)
     * or above them.
     *
     * @param aboveButtons if <code>true</code> status line is placed above buttons; if
     * 	<code>false</code> to the right
     */
    public void setStatusLineAboveButtons(boolean aboveButtons) {
        fStatusLineAboveButtons = aboveButtons;
    }

    /**
     * Sets the image for this dialog.
     * @param image the image.
     */
    public void setImage(Image image) {
        fImage = image;
    }

    /**
     * Returns the first element from the list of results. Returns <code>null</code>
     * if no element has been selected.
     *
     * @return the first result element if one exists. Otherwise <code>null</code> is
     *  returned.
     */
    public Object getFirstResult() {
        Object[] result = getResult();
        if (result == null || result.length == 0) {
