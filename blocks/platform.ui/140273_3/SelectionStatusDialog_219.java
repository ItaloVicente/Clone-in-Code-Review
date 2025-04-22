	private MessageLine fStatusLine;

	private IStatus fLastStatus;

	private Image fImage;

	private boolean fStatusLineAboveButtons = false;

	public SelectionStatusDialog(Shell parent) {
		super(parent);
	}

	public void setStatusLineAboveButtons(boolean aboveButtons) {
		fStatusLineAboveButtons = aboveButtons;
	}

	public void setImage(Image image) {
		fImage = image;
	}

	public Object getFirstResult() {
		Object[] result = getResult();
		if (result == null || result.length == 0) {
