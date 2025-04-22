	protected Text createTextWidget(Composite parent) {
		if (heigthInChars > 1) {
			return new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		}
		return new Text(parent, SWT.SINGLE | SWT.BORDER);
	}

