		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			final Shell shell = new View(viewModel).createShell();
			Display display1 = Display.getCurrent();
			while (!shell.isDisposed()) {
				if (!display1.readAndDispatch()) {
					display1.sleep();
