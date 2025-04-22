		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				ViewModel viewModel = new ViewModel();
				Shell shell = new View(viewModel).createShell();

				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
