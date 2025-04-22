			Shell shell = new Snippet026AnonymousBeanProperties().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
