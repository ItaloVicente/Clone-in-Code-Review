	public static final int NONE = 0;

	public static final int OVERRIDE = 1;

	public static final int MINIMUM = 2;

	public static final int MAXIMUM = 3;

	public int hintType = OVERRIDE;

	public int widthHint = SWT.DEFAULT;

	public int heightHint = SWT.DEFAULT;

	public int verticalSpan = 1;

	public int horizontalSpan = 1;

	public int horizontalAlignment = SWT.FILL;

	public int verticalAlignment = SWT.FILL;

	public int horizontalIndent = 0;

	public int verticalIndent = 0;

	public CellData() {
	}

	public CellData(GridData data) {
		verticalSpan = data.verticalSpan;
		horizontalSpan = data.horizontalSpan;

		switch (data.horizontalAlignment) {
		case GridData.BEGINNING:
			horizontalAlignment = SWT.LEFT;
			break;
		case GridData.CENTER:
			horizontalAlignment = SWT.CENTER;
			break;
		case GridData.END:
			horizontalAlignment = SWT.RIGHT;
			break;
		case GridData.FILL:
			horizontalAlignment = SWT.FILL;
			break;
		}

		switch (data.verticalAlignment) {
		case GridData.BEGINNING:
			verticalAlignment = SWT.LEFT;
			break;
		case GridData.CENTER:
			verticalAlignment = SWT.CENTER;
			break;
		case GridData.END:
			verticalAlignment = SWT.RIGHT;
			break;
		case GridData.FILL:
			verticalAlignment = SWT.FILL;
			break;
		}

		widthHint = data.widthHint;
		heightHint = data.heightHint;
		horizontalIndent = data.horizontalIndent;
		hintType = OVERRIDE;
	}

	public CellData(CellData newData) {
		hintType = newData.hintType;
		widthHint = newData.widthHint;
		heightHint = newData.heightHint;
		horizontalAlignment = newData.horizontalAlignment;
		verticalAlignment = newData.verticalAlignment;
		horizontalSpan = newData.horizontalSpan;
		verticalSpan = newData.verticalSpan;
	}

	public CellData setHint(int hintType, Point hint) {
		return setHint(hintType, hint.x, hint.y);
	}

	public CellData setHint(int hintType, int horizontal, int vertical) {
		this.hintType = hintType;
		this.heightHint = vertical;
		this.widthHint = horizontal;

		return this;
	}

	public CellData align(int horizontalAlignment, int verticalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;

		return this;
	}

	public CellData span(int horizontalSpan, int verticalSpan) {
		this.horizontalSpan = horizontalSpan;
		this.verticalSpan = verticalSpan;

		return this;
	}

	public CellData indent(Point indent) {
		return this.indent(indent.x, indent.y);
	}

	public CellData indent(int horizontalIndent, int verticalIndent) {
		this.horizontalIndent = horizontalIndent;
		this.verticalIndent = verticalIndent;

		return this;
	}

	public Point computeSize(SizeCache toCompute, int cellWidth, int cellHeight) {

		int absHorizontalIndent = Math.abs(horizontalIndent);
		int absVerticalIndent = Math.abs(verticalIndent);

		if (cellWidth != SWT.DEFAULT) {
			cellWidth -= absHorizontalIndent;
		}

		if (cellHeight != SWT.DEFAULT) {
			cellHeight -= absVerticalIndent;
		}

		int controlWidth = horizontalAlignment == SWT.FILL ? cellWidth : SWT.DEFAULT;
		int controlHeight = verticalAlignment == SWT.FILL ? cellHeight : SWT.DEFAULT;

		Point controlSize = computeControlSize(toCompute, controlWidth, controlHeight);

		if (cellWidth != SWT.DEFAULT && controlSize.x > cellWidth) {
			controlSize = computeControlSize(toCompute, cellWidth, controlHeight);
			if (cellHeight != SWT.DEFAULT && controlSize.y > cellHeight) {
				controlSize.y = cellHeight;
			}
		} else if (cellHeight != SWT.DEFAULT && controlSize.y > cellHeight) {
			controlSize = computeControlSize(toCompute, controlWidth, cellHeight);
			if (cellWidth != SWT.DEFAULT && controlSize.x > cellWidth) {
				controlSize.x = cellWidth;
			}
		}

		controlSize.x += absHorizontalIndent;
		controlSize.y += absVerticalIndent;

		return controlSize;
	}

	public void positionControl(SizeCache cache, Rectangle cellBounds) {

		int startx = cellBounds.x;
		int starty = cellBounds.y;
		int availableWidth = cellBounds.width - horizontalIndent;
		int availableHeight = cellBounds.height - verticalIndent;

		Point size = computeSize(cache, availableWidth, availableHeight);

		switch (horizontalAlignment) {
		case SWT.RIGHT:
			startx = cellBounds.x + availableWidth - size.x;
			break;
		case SWT.CENTER:
			startx = cellBounds.x + (availableWidth - size.x) / 2;
			break;
		}

		switch (verticalAlignment) {
		case SWT.BOTTOM:
			starty = cellBounds.y + availableHeight - size.y;
			break;
		case SWT.CENTER:
			starty = cellBounds.y + (availableHeight - size.y) / 2;
			break;
		}

		cache.getControl().setBounds(startx + horizontalIndent, starty + verticalIndent, size.x, size.y);
	}

	private Point computeControlSize(SizeCache toCompute, int controlWidth, int controlHeight) {
		switch (hintType) {
		case OVERRIDE:
			return computeOverrideSize(toCompute, controlWidth, controlHeight, widthHint, heightHint);
		case MINIMUM:
			return computeMinimumBoundedSize(toCompute, controlWidth, controlHeight, widthHint, heightHint);
		case MAXIMUM:
			return computeMaximumBoundedSize(toCompute, controlWidth, controlHeight, widthHint, heightHint);
		}

		return computeRawSize(toCompute, controlWidth, controlHeight);
	}

	private static Point computeRawSize(SizeCache toCompute, int controlWidth, int controlHeight) {
		if (controlWidth != SWT.DEFAULT && controlHeight != SWT.DEFAULT) {
			return new Point(controlWidth, controlHeight);
		}

		Point result = toCompute.computeSize(controlWidth, controlHeight);

		if (controlWidth != SWT.DEFAULT) {
			result.x = controlWidth;
		} else if (controlHeight != SWT.DEFAULT) {
			result.y = controlHeight;
		}

		return result;
	}

	private static Point computeOverrideSize(SizeCache control, int wHint, int hHint, int overrideW, int overrideH) {
		int resultWidth = overrideW;
		int resultHeight = overrideH;

		if (wHint != SWT.DEFAULT) {
			resultWidth = wHint;
		}

		if (hHint != SWT.DEFAULT) {
			resultHeight = hHint;
		}

		if (resultWidth == SWT.DEFAULT || resultHeight == SWT.DEFAULT) {
			Point result = computeRawSize(control, resultWidth, resultHeight);

			return result;
		}

		return new Point(resultWidth, resultHeight);
	}

	private static Point computeMaximumBoundedSize(SizeCache control, int wHint, int hHint, int boundedWidth,
			int boundedHeight) {
		Point controlSize = computeRawSize(control, wHint, hHint);

		if (wHint == SWT.DEFAULT && boundedWidth != SWT.DEFAULT && controlSize.x > boundedWidth) {
			return computeMaximumBoundedSize(control, boundedWidth, hHint, boundedWidth, boundedHeight);
		}

		if (hHint == SWT.DEFAULT && boundedHeight != SWT.DEFAULT && controlSize.y > boundedHeight) {
			return computeMaximumBoundedSize(control, wHint, boundedHeight, boundedWidth, boundedHeight);
		}

		return controlSize;
	}

	private static Point computeMinimumBoundedSize(SizeCache control, int wHint, int hHint, int minimumWidth,
			int minimumHeight) {

		Point controlSize = computeRawSize(control, wHint, hHint);

		if (minimumWidth != SWT.DEFAULT && wHint == SWT.DEFAULT && controlSize.x < minimumWidth) {
			return computeMinimumBoundedSize(control, minimumWidth, hHint, minimumWidth, minimumHeight);
		}

		if (minimumHeight != SWT.DEFAULT && hHint == SWT.DEFAULT && controlSize.y < minimumHeight) {
			return computeMinimumBoundedSize(control, wHint, minimumHeight, minimumWidth, minimumHeight);
		}

		return controlSize;
	}
