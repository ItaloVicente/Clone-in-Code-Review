	private org.eclipse.swt.widgets.List list;

	public ListViewer(Composite parent) {
		this(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	public ListViewer(Composite parent, int style) {
		this(new org.eclipse.swt.widgets.List(parent, style));
	}

	public ListViewer(org.eclipse.swt.widgets.List list) {
		this.list = list;
		hookControl(list);
	}

	@Override
