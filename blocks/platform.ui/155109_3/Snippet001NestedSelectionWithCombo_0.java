		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new View().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
