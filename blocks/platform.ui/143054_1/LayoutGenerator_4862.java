	private static final Point defaultSize = new Point(150, 150);

	private static final int wrapSize = 350;

	private static final GridDataFactory nonWrappingLabelData = GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(false, false);

	private static boolean hasStyle(Control c, int style) {
		return (c.getStyle() & style) != 0;
	}

	public static void generateLayout(Composite toGenerate) {
		Control[] children = toGenerate.getChildren();

		for (Control control : children) {
			if (control.getLayoutData() != null) {
				continue;
			}

			applyLayoutDataTo(control);
		}
	}

	private static void applyLayoutDataTo(Control control) {
		defaultsFor(control).applyTo(control);
	}

	public static GridDataFactory defaultsFor(Control control) {
		if (control instanceof Button) {
			Button button = (Button) control;

			if (hasStyle(button, SWT.CHECK)) {
				return nonWrappingLabelData.copy();
			}
			return GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).hint(Geometry.max(button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true), LayoutConstants.getMinButtonSize()));
		}

		if (control instanceof Scrollable) {
			Scrollable scrollable = (Scrollable) control;

			if (scrollable instanceof Composite) {
				Composite composite = (Composite) control;

				Layout theLayout = composite.getLayout();
				if (theLayout instanceof GridLayout) {
					boolean growsHorizontally = false;
					boolean growsVertically = false;

					Control[] children = composite.getChildren();
					for (Control child : children) {
						GridData data = (GridData) child.getLayoutData();

						if (data != null) {
							if (data.grabExcessHorizontalSpace) {
								growsHorizontally = true;
							}
							if (data.grabExcessVerticalSpace) {
								growsVertically = true;
							}
						}
					}

					return GridDataFactory.fillDefaults().grab(growsHorizontally, growsVertically);
				}
			}
		}

		boolean wrapping = hasStyle(control, SWT.WRAP);
