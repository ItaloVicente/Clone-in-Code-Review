	public ProgressIndicator(Composite parent, int style) {
		super(parent, SWT.NONE);
		state = 1;
		if ((style & SWT.VERTICAL) == 0) {
			style |= SWT.HORIZONTAL;
		}
		this.style = style;
	}
