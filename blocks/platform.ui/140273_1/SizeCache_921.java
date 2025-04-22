	private Control control;

	private Point preferredSize;

	private Point cachedWidth;

	private Point cachedHeight;

	private boolean flushChildren;

	private boolean independentDimensions = false;

	private boolean preferredWidthOrLargerIsMinimumHeight = false;

	private int widthAdjustment = 0;

	private int heightAdjustment = 0;


	public SizeCache() {
		this(null);
	}

	public SizeCache(Control control) {
		setControl(control);
	}

	public void setControl(Control newControl) {
		if (newControl != control && !newControl.isDisposed()) {
			control = newControl;
			if (control == null) {
				independentDimensions = true;
				preferredWidthOrLargerIsMinimumHeight = false;
				widthAdjustment = 0;
				heightAdjustment = 0;
			} else {
				independentDimensions = independentLengthAndWidth(control);
				preferredWidthOrLargerIsMinimumHeight = isPreferredWidthMaximum(control);
				computeHintOffset(control);
				flush();
			}
		}
	}

	public Control getControl() {
		return control;
	}

	public void flush() {
		flush(true);
	}

	public void flush(boolean recursive) {
		preferredSize = null;
		cachedWidth = null;
		cachedHeight = null;
		this.flushChildren = recursive;
	}

	private Point getPreferredSize() {
		if (preferredSize == null) {
			preferredSize = computeSize(control, SWT.DEFAULT, SWT.DEFAULT);
		}

		return preferredSize;
	}

	public Point computeSize(int widthHint, int heightHint) {
		if (control == null) {
			return new Point(0, 0);
		}

		if (widthHint != SWT.DEFAULT && heightHint != SWT.DEFAULT) {
			return new Point(widthHint, heightHint);
		}

		if (widthHint == SWT.DEFAULT && heightHint == SWT.DEFAULT) {
			return Geometry.copy(getPreferredSize());
		}

		if (independentDimensions) {
			Point result = Geometry.copy(getPreferredSize());

			if (widthHint != SWT.DEFAULT) {
				result.x = widthHint;
			}

			if (heightHint != SWT.DEFAULT) {
				result.y = heightHint;
			}

			return result;
		}

		if (heightHint == SWT.DEFAULT) {
			if (preferredSize != null) {
				if (widthHint == preferredSize.x) {
					return Geometry.copy(preferredSize);
				}
			}

			if (cachedHeight != null) {
				if (cachedHeight.x == widthHint) {
					return Geometry.copy(cachedHeight);
				}
			}

			if (preferredWidthOrLargerIsMinimumHeight) {
				getPreferredSize();

				if (widthHint >= preferredSize.x) {
					Point result = Geometry.copy(preferredSize);
					result.x = widthHint;
					return result;
				}
			}

			cachedHeight = computeSize(control, widthHint, heightHint);

			return Geometry.copy(cachedHeight);
		}

		if (widthHint == SWT.DEFAULT) {
			if (preferredSize != null) {
				if (heightHint == preferredSize.y) {
					return Geometry.copy(preferredSize);
				}
			}

			if (cachedWidth != null) {
				if (cachedWidth.y == heightHint) {
					return Geometry.copy(cachedWidth);
				}
			}

			cachedWidth = computeSize(control, widthHint, heightHint);

			return Geometry.copy(cachedWidth);
		}

		return computeSize(control, widthHint, heightHint);
	}

	private Point computeSize(Control control, int widthHint, int heightHint) {
		int adjustedWidthHint = widthHint == SWT.DEFAULT ? SWT.DEFAULT : Math.max(0, widthHint - widthAdjustment);
		int adjustedHeightHint = heightHint == SWT.DEFAULT ? SWT.DEFAULT : Math.max(0, heightHint - heightAdjustment);

		Point result = control.computeSize(adjustedWidthHint, adjustedHeightHint, flushChildren);
		flushChildren = false;


		if (widthHint != SWT.DEFAULT) {
			result.x = widthHint;
		}

		if (heightHint != SWT.DEFAULT) {
			result.y = heightHint;
		}

		return result;
	}

	static boolean independentLengthAndWidth(Control control) {
		if (control == null) {
			return true;
		}

		if (control instanceof Button || control instanceof ProgressBar || control instanceof Sash
				|| control instanceof Scale || control instanceof Slider || control instanceof List
				|| control instanceof Combo || control instanceof Tree) {
			return true;
		}

		if (control instanceof Label || control instanceof Text) {
			return (control.getStyle() & SWT.WRAP) == 0;
		}


		return false;
	}

	private void computeHintOffset(Control control) {
		if (control instanceof Composite) {
			Composite composite = (Composite) control;
			Rectangle trim = composite.computeTrim(0, 0, 0, 0);

			widthAdjustment = trim.width;
			heightAdjustment = trim.height;
		} else {
			widthAdjustment = control.getBorderWidth() * 2;
			heightAdjustment = widthAdjustment;
		}
	}

	private static boolean isPreferredWidthMaximum(Control control) {
		return (control instanceof ToolBar
				|| control instanceof Label);
	}
