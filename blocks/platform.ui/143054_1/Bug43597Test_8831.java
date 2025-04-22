		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		GridLayout gridLayout = new GridLayout();
		shell.setLayout(gridLayout);
		Text text = new Text(shell, SWT.LEFT);
		textFont = new Font(text.getDisplay(),
				"Lucida Grande", 13, SWT.NORMAL);
