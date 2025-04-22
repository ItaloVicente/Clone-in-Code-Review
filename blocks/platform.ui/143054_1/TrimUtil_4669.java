	public static final int TRIM_DEFAULT_HEIGHT;
	static {
		Shell s = new Shell(Display.getCurrent(), SWT.NONE);
		s.setLayout(new GridLayout());
		ToolBar t = new ToolBar(s, SWT.NONE);
		t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		ToolItem ti = new ToolItem(t, SWT.PUSH);
		ti.setImage(JFaceResources.getImageRegistry().get(Dialog.DLG_IMG_MESSAGE_INFO));
		s.layout();
		int toolItemHeight = t.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		GC gc = new GC(s);
		Point fontSize = gc.textExtent("Wg"); //$NON-NLS-1$
		gc.dispose();
		TRIM_DEFAULT_HEIGHT = Math.max(toolItemHeight, fontSize.y);
		s.dispose();
