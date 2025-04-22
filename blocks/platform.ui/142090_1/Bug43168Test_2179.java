	public void testStackOverflow() throws ParseException {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());
		Text text = new Text(shell, SWT.BORDER);
		KeySequenceText keySequenceText = new KeySequenceText(text);
