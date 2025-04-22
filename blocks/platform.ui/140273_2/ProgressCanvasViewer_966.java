	ProgressCanvasViewer(Composite parent, int style, int itemsToShow, int numChars, int orientation) {
		super();
		this.orientation = orientation;
		numShowItems = itemsToShow;
		maxCharacterWidth = numChars;
		canvas = new Canvas(parent, style);
		hookControl(canvas);
		GC gc = new GC(canvas);
		gc.setFont(JFaceResources.getDefaultFont());
		fontMetrics = gc.getFontMetrics();
		gc.dispose();
		initializeListeners();
	}

	@Override
