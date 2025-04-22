    /**
     * Property name that signifies the selected color of this
     * <code>ColorSelector</code> has changed.
     *
     * @since 3.0
     */

    private Button fButton;

    private Color fColor;

    private RGB fColorValue;

    private Point fExtent;

    private Image fImage;

    /**
     * Create a new instance of the reciever and the button that it wrappers in
     * the supplied parent <code>Composite</code>.
     *
     * @param parent
     *            The parent of the button.
     */
    public ColorSelector(Composite parent) {
        fButton = new Button(parent, SWT.PUSH);
        fExtent = computeImageSize(parent);
        fImage = new Image(parent.getDisplay(), fExtent.x, fExtent.y);
        GC gc = new GC(fImage);
        gc.setBackground(fButton.getBackground());
        gc.fillRectangle(0, 0, fExtent.x, fExtent.y);
        gc.dispose();
        fButton.setImage(fImage);
        fButton.addSelectionListener(widgetSelectedAdapter(event -> open()));
        fButton.addDisposeListener(event -> {
		    if (fImage != null) {
		        fImage.dispose();
		        fImage = null;
		    }
		    if (fColor != null) {
		        fColor.dispose();
		        fColor = null;
		    }
