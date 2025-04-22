
		addListener(SWT.Paint, l);
		addListener(SWT.KeyDown, l);
		addListener(SWT.MouseDown, l);
		addListener(SWT.MouseDoubleClick, l);
		getParent().addListener(SWT.FocusIn,l);
