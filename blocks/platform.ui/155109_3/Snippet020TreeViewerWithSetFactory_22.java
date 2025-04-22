			Shell shell = new Snippet020TreeViewerWithSetFactory().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
