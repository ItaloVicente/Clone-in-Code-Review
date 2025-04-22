	}

	protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		int averageWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		double horizontalDialogUnitSize = averageWidth * 0.25;

		return (int) Math.round(dlus * horizontalDialogUnitSize);
	}

	protected int convertVerticalDLUsToPixels(Control control, int dlus) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		int height = gc.getFontMetrics().getHeight();
		gc.dispose();

		double verticalDialogUnitSize = height * 0.125;

		return (int) Math.round(dlus * verticalDialogUnitSize);
	}

	protected void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = getNumberOfControls();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = HORIZONTAL_GAP;
		parent.setLayout(layout);
		doFillIntoGrid(parent, layout.numColumns);
	}

	public void dispose() {
	}

	protected abstract void doFillIntoGrid(Composite parent, int numColumns);

	protected abstract void doLoad();

	protected abstract void doLoadDefault();

	protected abstract void doStore();

	public void fillIntoGrid(Composite parent, int numColumns) {
		Assert.isTrue(numColumns >= getNumberOfControls());
		Assert.isTrue(parent.getLayout() instanceof GridLayout);
		doFillIntoGrid(parent, numColumns);
	}

	protected void fireStateChanged(String property, boolean oldValue,
			boolean newValue) {
		if (oldValue == newValue) {
