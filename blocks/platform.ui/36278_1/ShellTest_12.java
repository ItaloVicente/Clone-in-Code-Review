	@Test
	public void test375069AllShell() {
		Display display = Display.getDefault();
		CSSEngine engine = createEngine("Shell { font-style: italic; }", display);

		Shell parent = new Shell(display, SWT.NONE);
		WidgetElement.setCSSClass(parent, "parent");
		Shell child = new Shell(parent, SWT.NONE);
