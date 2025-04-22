	private GridData data;

	private GridDataFactory(GridData d) {
		this.data = d;
	}

	public static GridDataFactory swtDefaults() {
		return new GridDataFactory(new GridData());
	}

	public static GridDataFactory createFrom(GridData data) {
		return new GridDataFactory(copyData(data));
	}

	public static GridDataFactory fillDefaults() {
		GridData data = new GridData();
		data.minimumWidth = 1;
		data.minimumHeight = 1;
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment = SWT.FILL;

		return new GridDataFactory(data);
	}

	public static GridDataFactory defaultsFor(Control theControl) {
		return LayoutGenerator.defaultsFor(theControl);
	}

	public static void generate(Control theControl, int hSpan, int vSpan) {
		defaultsFor(theControl).span(hSpan, vSpan).applyTo(theControl);
	}

	public static void generate(Control theControl, Point span) {
		defaultsFor(theControl).span(span).applyTo(theControl);
	}

	public GridDataFactory span(int hSpan, int vSpan) {
		data.horizontalSpan = hSpan;
		data.verticalSpan = vSpan;
		return this;
	}

	public GridDataFactory span(Point span) {
		data.horizontalSpan = span.x;
		data.verticalSpan = span.y;
		return this;
	}

	public GridDataFactory hint(int xHint, int yHint) {
		data.widthHint = xHint;
		data.heightHint = yHint;
		return this;
	}

	public GridDataFactory hint(Point hint) {
		data.widthHint = hint.x;
		data.heightHint = hint.y;
		return this;
	}

	public GridDataFactory align(int hAlign, int vAlign) {
		if (hAlign != SWT.BEGINNING && hAlign != SWT.CENTER && hAlign != GridData.CENTER && hAlign != SWT.END && hAlign != GridData.END && hAlign != SWT.FILL && hAlign != SWT.LEFT && hAlign != SWT.RIGHT) {
			throw new IllegalArgumentException();
		}
		if (vAlign != SWT.BEGINNING && vAlign != SWT.CENTER && vAlign != GridData.CENTER && vAlign != SWT.END && vAlign != GridData.END && vAlign != SWT.FILL && vAlign != SWT.TOP && vAlign != SWT.BOTTOM) {
			throw new IllegalArgumentException();
		}
		data.horizontalAlignment = hAlign;
		data.verticalAlignment = vAlign;
		return this;
	}

	public GridDataFactory indent(int hIndent, int vIndent) {
		data.horizontalIndent = hIndent;
		data.verticalIndent = vIndent;
		return this;
	}

	public GridDataFactory indent(Point indent) {
		data.horizontalIndent = indent.x;
		data.verticalIndent = indent.y;
		return this;
	}

	public GridDataFactory grab(boolean horizontal, boolean vertical) {
		data.grabExcessHorizontalSpace = horizontal;
		data.grabExcessVerticalSpace = vertical;
		return this;
	}

	public GridDataFactory minSize(int minX, int minY) {
		data.minimumWidth = minX;
		data.minimumHeight = minY;
		return this;
	}

	public GridDataFactory minSize(Point min) {
		data.minimumWidth = min.x;
		data.minimumHeight = min.y;
		return this;
	}

	public GridDataFactory exclude(boolean shouldExclude) {
		data.exclude = shouldExclude;
		return this;
	}

	public GridData create() {
		return copyData(data);
	}

	public GridDataFactory copy() {
		return new GridDataFactory(create());
	}

	public static GridData copyData(GridData data) {
		GridData newData = new GridData(data.horizontalAlignment, data.verticalAlignment, data.grabExcessHorizontalSpace, data.grabExcessVerticalSpace, data.horizontalSpan,
				data.verticalSpan);
		newData.exclude = data.exclude;
		newData.heightHint = data.heightHint;
		newData.horizontalIndent = data.horizontalIndent;
		newData.minimumHeight = data.minimumHeight;
		newData.minimumWidth = data.minimumWidth;
		newData.verticalIndent = data.verticalIndent;
		newData.widthHint = data.widthHint;

		return newData;
	}

	public void applyTo(Control control) {
		control.setLayoutData(create());
	}
