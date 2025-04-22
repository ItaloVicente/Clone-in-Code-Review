	private Control control;

	private String title = null;

	private String description = null;

	private Image image = null;

	private ImageDescriptor imageDescriptor = null;

	private String message = null;

	private int messageType = NONE;

	private String errorMessage = null;

	private FontMetrics fontMetrics;

	protected DialogPage() {
	}

	protected DialogPage(String title) {
		this.title = title;
	}

	protected DialogPage(String title, ImageDescriptor image) {
		this(title);
		imageDescriptor = image;
	}

	protected int convertHeightInCharsToPixels(int chars) {
		if (fontMetrics == null) {
