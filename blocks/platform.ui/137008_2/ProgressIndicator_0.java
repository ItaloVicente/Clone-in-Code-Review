	public ProgressIndicator(Composite parent, int style) {
		super(parent, SWT.NONE);
		if ((style & SWT.VERTICAL) == 0) {
			style |= SWT.HORIZONTAL;
		}
		this.styleForProgressBar = style;
	}
