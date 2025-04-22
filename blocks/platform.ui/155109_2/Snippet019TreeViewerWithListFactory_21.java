			Shell shell = new Snippet019TreeViewerWithListFactory().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
