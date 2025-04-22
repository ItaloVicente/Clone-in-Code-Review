	Canvas canvas = null;

	public TrimFrame(Composite parent) {
		createControl(parent);
	}

	private void createControl(Composite parent) {
		dispose();
		canvas = new Canvas(parent, SWT.NONE);
		canvas.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		canvas.addPaintListener(new PaintListener() {
			private void drawLine(GC gc, int x1, int y1, int x2, int y2, boolean flipXY) {
				if (flipXY) {
					int tmp = x1;
					x1 = y1;
					y1 = tmp;
					tmp = x2;
					x2 = y2;
					y2 = tmp;
				}

				gc.drawLine(x1, y1, x2, y2);
			}

			@Override
