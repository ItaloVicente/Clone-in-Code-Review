	public static final String PROP_COLORCHANGE = "colorValue"; //$NON-NLS-1$

	private Button fButton;

	private Color fColor;

	private RGB fColorValue;

	private Point fExtent;

	private Image fImage;

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
