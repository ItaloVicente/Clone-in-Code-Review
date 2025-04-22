		ViewModel viewModel = new ViewModel();
		Shell shell = new View(viewModel).createShell();

		Display display = Display.getCurrent();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
