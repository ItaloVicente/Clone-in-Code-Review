	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display, SWT.CLOSE);
		shell.setSize(400, 400);
		shell.setLayout(new GridLayout());

		Snippet006TableMultiLineCells example = new Snippet006TableMultiLineCells();
		example.createPartControl(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
