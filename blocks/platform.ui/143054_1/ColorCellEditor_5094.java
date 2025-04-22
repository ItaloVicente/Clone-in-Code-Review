			colorLabel.setBounds(-1, 0, colorSize.x, colorSize.y);
			rgbLabel.setBounds(colorSize.x + GAP - 1, ty, bounds.width
					- colorSize.x - GAP, bounds.height);
		}
	}

	public ColorCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	public ColorCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue(new RGB(0, 0, 0));
	}

	private ImageData createColorImage(Control w, RGB color) {

		GC gc = new GC(w);
		FontMetrics fm = gc.getFontMetrics();
		int size = fm.getAscent();
		gc.dispose();

		int indent = 6;
		int extent = DEFAULT_EXTENT;
		if (w instanceof Table) {
